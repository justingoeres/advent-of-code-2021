package org.jgoeres.adventofcode2021.Day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16Service {
    public boolean DEBUG = false;

    private ArrayList<Integer> inputList = new ArrayList<>();

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
        inputList.clear();
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
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
