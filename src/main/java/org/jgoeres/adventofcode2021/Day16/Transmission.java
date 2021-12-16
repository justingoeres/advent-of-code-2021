package org.jgoeres.adventofcode2021.Day16;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Transmission {
    private final ArrayList<Integer> packetBytes;
    private final Iterator<Integer> iter;
    private Integer bitsLeft = 0;
    private Integer currentByte = 0;
    public static final int BYTE_WIDTH = 8;

    public Transmission(ArrayList<Integer> packetBytes) {
        this.packetBytes = packetBytes;
        this.iter = packetBytes.iterator();
    }

    public Integer getNextValue(final Integer width) {
        // width is in bits!
        // If we don't have enough bits in the current buffer
        // to fulfill this request, get more and move the pointer
        if (bitsLeft < width) {
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

    public Integer getLiteralValue() {                // literal values are 4-bit numbers prefixed by
        //     a 1 if NOT the final group
        //     a 0 if YES the final group
        Integer literalValue = 0;
        final Integer LITERAL_GROUP_WIDTH = 5;
        while (true) {  // do it until we hit an end group
            Integer groupValueRaw = this.getNextValue(LITERAL_GROUP_WIDTH);
            literalValue =
                    literalValue << (LITERAL_GROUP_WIDTH - 1) ^ (groupValueRaw & 0b1111);
            // Stop if top bit is 0
            if ((groupValueRaw & 0b10000) == 0) {
                break;
            }
        }
        // When we're done with a packet, sweep out the trailing zeroes
        this.alignNextPacket();
        System.out.println("Literal value: " + literalValue);
        return literalValue;
    }

    public void alignNextPacket() {
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
        return;
    }
}
