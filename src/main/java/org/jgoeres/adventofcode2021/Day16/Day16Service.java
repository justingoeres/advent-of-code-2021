package org.jgoeres.adventofcode2021.Day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16Service {
    public boolean DEBUG = false;

    private Transmission transmission;

    public Day16Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day16Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 16A ===");

        long result = 0;
        /**
         * Decode the structure of your hexadecimal-encoded BITS transmission;
         * what do you get if you add up the version numbers in all packets?
         **/

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

        Integer version = transmission.getNextValue(3);
        Integer typeID = transmission.getNextValue(3);
        switch (typeID) {
            case 4: // literal value
                Integer literValue = transmission.getLiteralValue();
                break;
            default:
                break;
        }

//        Iterator<Integer> iter = inputList.iterator();
//        // Parse a packet!
//        // Get the next byte
//        Integer packetByte = iter.next();
//        // Version is top 3 bits
//        final Integer versionMask = 0b11100000;
//        final Integer versionShift = 5;
//        // Type ID is next 3 bits
//        final Integer typeIDMask = 0b00011100;
//        final Integer typeIDShift = 2;
//
//        Integer version = (packetByte & versionMask) >> versionShift;
//        Integer typeID = (packetByte & typeIDMask) >> typeIDShift;
//
//        packetByte &=
//                0b00000011;   // Drop the version & type ID to leave just the begining of the value
//        Integer bitsLeft = 2;   // 2 unprocessed bits
//        switch (typeID) {
//            case 4: // literal value
//                if (bitsLeft < 5) {
//                    // If we don't have enough bits available to check, get some!
//                    Integer nextByte = iter.next();
//                    packetByte = (packetByte << 8) ^ nextByte;
//                    bitsLeft += 8;
//                    // Now we have 8 more bits!
//                    // Mask the top 5
//                    Integer mask = 0b11111 << (bitsLeft -= 5);
//                    // Mask the next group & shift it down so we can work with it
//                    Integer nextGroup = (packetByte & mask) >> bitsLeft;
//                    Boolean moreGroups;
//                    Integer groupValue = 0;
//                    while (moreGroups = (nextGroup & 0b10000) > 0))
//                    Integer nextValue = nextGroup & 0b1111;
//                    groupValue = groupValue << 4 + nextValue;
//
//                }
//                break;
//            default: // operator
//                // TODO
//                break;
//    }

        System.out.println("Day 16A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 16B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 16B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        final ArrayList<Integer> inputList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            /** Replace this regex **/
            Pattern p = Pattern.compile("([0-9a-fA-F]{2})");
            line = br.readLine();   // only one line in this puzzle input
            // process the line.
            Matcher m = p.matcher(line);
            while (m.find()) { // If our regex matched this line
                // Parse it
                Integer field1 = Integer.parseInt(m.group(1), 16);
                inputList.add(field1);
            }
            transmission = new Transmission(inputList);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
