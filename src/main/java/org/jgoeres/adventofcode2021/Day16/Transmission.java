package org.jgoeres.adventofcode2021.Day16;

import org.jgoeres.adventofcode2021.Day16.packet.LiteralValuePacket;
import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;
import org.jgoeres.adventofcode2021.Day16.packet.Packet;
import org.jgoeres.adventofcode2021.Day16.packet.operator.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Transmission {
    private final Iterator<Integer> iter;
    private Integer bitsLeft = 0;
    private Integer currentByte = 0;
    public static final int BYTE_WIDTH = 8;

    private final List<Packet> packets = new ArrayList<>();

    public Transmission(ArrayList<Integer> packetBytes) {
        this.iter = packetBytes.iterator();
    }

    public Integer getNextValue(final Integer width) {
        // width is in bits!
        // If we don't have enough bits in the current buffer
        // to fulfill this request, get more and move the pointer
        while (bitsLeft < width) {
            // Get the next byte
            final Integer nextByte = iter.next();
//            System.out.println("Read Byte:\t" + String.format("0x%02X", nextByte));
            // Shift the current buffer up to make room
            currentByte <<= BYTE_WIDTH;
            // Add the nextByte in
            currentByte ^= nextByte;
            // Update the # of bitsLeft
            bitsLeft += BYTE_WIDTH;
        }
        // Now that we have enough bits, mask out the value.
        // Make the mask
        Integer mask = 0;
        for (int i = 0; i < width; i++) {
            mask = (mask << 1) ^ 1;
        }
        // Shift the mask to where we need it
        mask <<= bitsLeft - width;
        // Mask to get our answer
        Integer result = currentByte & mask;
        // Zero out what we masked
        currentByte &= ~mask;
        // Update our bitsLeft for what we just took
        bitsLeft -= width;
        // Shift our answer back
        result >>= bitsLeft;
        // return!
        return result;
    }

    public Packet getOuterPacket() {
        return packets.get(0);
    }

    public void decode() {
        while (iter.hasNext()) {
            decodeNextPacket(packets);
            // When we're done with a packet, sweep out the trailing zeroes
            this.alignNextPacket();
        }
    }

    public Integer addUpVersions() {
        Integer total = 0;
        // Add up the version number for every packet (recursively) in this transmission
        for (Packet packet : packets) {
            total += packet.getTotalVersion();
        }
        return total;
    }

    public Long getTotalValue() {
        // Get the value of the whole transmission
        return 0L;
    }

    public Integer decodeNextPacket(List<Packet> packets) {
        // HEADER
        //  3 bits - packet version
        //  3 bits - type ID
        //      type ID: 4
        //          literal value
        //          literal values are 4-bit fields prefixed by
        //              a 1 if NOT the final group
        //              a 0 if YES the final group
        //              padded with leading zeroes to make a field length of multiple of 4 bits
        //      type ID: other
        //          operator packet
        //          contains one or more packets
        //          1 bit - length type ID
        //              0: next 15 bits are TOTAL LENGTH of subpackets
        //              1: next 11 bits are # of subpackets
        //          Followed by subpackets (which themselves can be literals or operators)
        //  Then the end is zero-padded to match up with byte boundaries? (I hope)
        Integer bitsConsumed = 0;

        Integer version = this.getNextValue(3);
        bitsConsumed += 3;
        Integer typeID = this.getNextValue(3);
        bitsConsumed += 3;

        switch (typeID) {
            case 4: // literal value
                bitsConsumed += decodeLiteralValuePacket(packets, version, typeID);
                break;
            default: // operator packet
                bitsConsumed += decodeOperatorPacket(packets, version, typeID);
                // Now that we've got the length of the contained packet(s)
                // in some form
                break;
        }
        return bitsConsumed;
    }

    private Integer decodeOperatorPacket(List<Packet> packets, Integer version,
                                         Integer typeID) {
        Integer bitsConsumed = 0;
        OperatorPacket operatorPacket = getOperatorPacketType(version, typeID);
        // get the length type ID
        Integer lengthTypeID = this.getNextValue(1);
        bitsConsumed += 1;
        Integer subpacketBitsConsumed = 0;
        if (lengthTypeID == 0) {
            // 0: next 15 bits are TOTAL LENGTH of subpackets
            Integer totalSubpacketLength = this.getNextValue(15);
            bitsConsumed += 15;
            while (subpacketBitsConsumed < totalSubpacketLength) {
                subpacketBitsConsumed +=
                        this.decodeNextPacket(operatorPacket.getChildPackets());
            }
        } else {
            // 1: next 11 bits are # of subpackets
            Integer numSubpackets = this.getNextValue(11);
            bitsConsumed += 11;
            for (int i = 0; i < numSubpackets; i++) {
                subpacketBitsConsumed += this.decodeNextPacket(operatorPacket.getChildPackets());
            }
        }
        bitsConsumed += subpacketBitsConsumed;
        packets.add(operatorPacket);
        return bitsConsumed;
    }

    private OperatorPacket getOperatorPacketType(Integer version, Integer typeId) {
        // Mini-factory for operator packets
        switch (typeId) {
            case 0: // sum
                return new SumPacket(version, typeId);
            case 1: // product
                return new ProductPacket(version, typeId);
            case 2: // min
                return new MinPacket(version, typeId);
            case 3: // max
                return new MaxPacket(version, typeId);
            case 5: // greater than
                return new GreaterThanPacket(version, typeId);
            case 6: // less than
                return new LessThanPacket(version, typeId);
            case 7: // equals
                return new EqualsPacket(version, typeId);
        }
        return null; // should never happen
    }

    private Integer decodeLiteralValuePacket(List<Packet> packets, Integer version,
                                             Integer typeID) {
        // literal values are 4-bit numbers prefixed by
        //     a 1 if NOT the final group
        //     a 0 if YES the final group
        Long literalValue = 0L;
        Integer bitsConsumed = 0;
        final Integer LITERAL_GROUP_WIDTH = 5;
        while (true) {  // do it until we hit an end group
            Integer groupValueRaw = this.getNextValue(LITERAL_GROUP_WIDTH);
            bitsConsumed += LITERAL_GROUP_WIDTH;
            literalValue =
                    literalValue << (LITERAL_GROUP_WIDTH - 1) ^ (groupValueRaw & 0b1111);
            // Stop if top bit is 0
            if ((groupValueRaw & 0b10000) == 0) {
                break;
            }
        }
//        System.out.println("Literal value: " + literalValue);
        packets.add(new LiteralValuePacket(literalValue, version, typeID));
        return bitsConsumed;
    }

    public Integer alignNextPacket() {
        // Each packet (apparently) starts on a byte boundary,
        // so all we need to do is "get" all the "bitsLeft" and discard them
        Integer dropped = this.getNextValue(bitsLeft);
        if (dropped > 0) {
            System.out.println(
                    MessageFormat.format("WARNING: Dropping {0} bits produced non-zero value {1}",
                            bitsLeft, dropped));
        } else {
            System.out.println(
                    MessageFormat.format("Aligning to next packet", dropped));
        }
        return dropped;
    }
}
