package org.jgoeres.adventofcode2021.Day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03Service {
    public boolean DEBUG = false;

    private ArrayList<Long> inputList = new ArrayList<>();

    private Integer bitWidth = null;

    public Day03Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day03Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 3A ===");

        /**
         * Use the binary numbers in your diagnostic report to calculate the
         * gamma rate and epsilon rate, then multiply them together. What is
         * the power consumption of the submarine? (Be sure to represent your
         * answer in decimal, not binary.)
         * **/

        // Each bit in the gamma rate can be determined by finding the
        // *most common bit* in the corresponding position of all numbers
        // in the diagnostic report

        int[] oneBitCounts = new int[bitWidth];
        int[] zeroBitCounts = new int[bitWidth];

        // Iterate over all the lines of the input
        for (Long rate : inputList) {
            // Count each bit as a gamma or epsilon
            for (int i = 0; i < bitWidth; i++) {
                if ((rate & (1L << i)) > 0) {
                    // If this bit is a one, add to the oneBitCount for this position
                    oneBitCounts[i]++;
                } else {
                    // else it's a zero, so count that
                    zeroBitCounts[i]++;
                }
            }
        }

        long gamma = 0;
        long epsilon = 0;
        // We have the bit counts, now calculate gamma
        for (int i = 0; i < bitWidth; i++) {
            if (oneBitCounts[i] > zeroBitCounts[i]) {
                // If 1s are more common in this position, put a 1 in gamma here
                gamma ^= 1 << i;
            } else {
                // 0s are more common here, so put a 1 in epsilon
                epsilon ^= 1 << i;
            }
        }

        System.out.println(MessageFormat.format("gamma:\t{0}\tepsilon:\t{1}", gamma, epsilon));
        long result = gamma * epsilon;
        System.out.println(MessageFormat.format("Day 3A: Answer = {0}", result));
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 3B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 3B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            /** Replace this regex **/
            while ((line = br.readLine()) != null) {
                if (bitWidth == null) {
                    // width of the gamma & epsilon numbers
                    // is the length of a line in the input
                    bitWidth = line.length();
                }
                inputList.add(Long.parseLong(line, 2));
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
