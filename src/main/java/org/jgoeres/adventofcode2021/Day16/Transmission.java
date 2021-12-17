package org.jgoeres.adventofcode2021.Day16;

import org.jgoeres.adventofcode2021.Day16.packet.LiteralValuePacket;
import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;
import org.jgoeres.adventofcode2021.Day16.packet.Packet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Transmission {
    private final ArrayList<Integer> packetBytes;
    private final Iterator<Integer> iter;
    private Integer bitsLeft = 0;
    private Integer currentByte = 0;
    public static final int BYTE_WIDTH = 8;

    private final List<Packet> packets = new ArrayList<>();

    public Transmission(ArrayList<Integer> packetBytes) {
        this.packetBytes = packetBytes;
        this.iter = packetBytes.iterator();
    }

    public Integer getNextValue(final Integer width) {
        // width is in bits!
        // If we don't have enough bits in the current buffer
        // to fulfill this request, get more and move the pointer
        while (bitsLeft < width) {
            // Get the next byte
            final Integer nextByte = iter.next();
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

    public void decode() {
        while (true) {
            decodeNextPacket(packets);
            // When we're done with a packet, sweep out the trailing zeroes
            this.alignNextPacket();
        }
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
                bitsConsumed += this.decodeLiteralValuePacket(packets, version, typeID);
//                literalValuePacket.setVersion(version);
//                literalValuePacket.setTypeID(typeID);
//                packets.add(literalValuePacket);
                break;
            default: // operator packet
                OperatorPacket operatorPacket = new OperatorPacket();
                // get the length type ID
                Integer lengthTypeID = this.getNextValue(1);
                if (lengthTypeID == 0) {
                    // 0: next 15 bits are TOTAL LENGTH of subpackets
                    Integer totalSubpacketLength = this.getNextValue(15);
                    Integer subpacketBitsConsumed = 0;
                    while (subpacketBitsConsumed < totalSubpacketLength) {
                        subpacketBitsConsumed +=
                                this.decodeNextPacket(operatorPacket.getChildPackets());
                    }
                    bitsConsumed += subpacketBitsConsumed;
                } else {
                    // 1: next 11 bits are # of subpackets
                    Integer numSubpackets = this.getNextValue(11);
                }
                packets.add(operatorPacket);
                // Now that we've got the length of the contained packet(s)
                // in some form
                break;
        }
        return bitsConsumed;
    }

    private OperatorPacket getOperatorPacket() {
        return null;
    }

    private Integer decodeLiteralValuePacket(List<Packet> packets, Integer version,
                                             Integer typeID) {
        // literal values are 4-bit numbers prefixed by
        //     a 1 if NOT the final group
        //     a 0 if YES the final group
        Integer literalValue = 0;
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
        System.out.println("Literal value: " + literalValue);
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
