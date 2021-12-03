package org.jgoeres.adventofcode2021.Day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03Service {
    public boolean DEBUG = false;

    private static final ArrayList<Long> inputList = new ArrayList<>();

    private Integer bitWidth;
    private int[] oneBitCounts;
    private int[] zeroBitCounts;
    Set<Long> temp;

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
        System.out.println("Day 3A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 3B ===");
        long oxygenGeneratorRating = 0;
        long co2ScrubberRating = 0;
        /**
         * To find oxygen generator rating, determine the most common value (0 or 1) in the current bit position,
         * and keep only numbers with that bit in that position.
         * If 0 and 1 are equally common, keep values with a 1 in the position being considered.
         * */
        Set<Long> oxygenGeneratorCandidates = new HashSet<>(inputList);
        Set<Long> toKeep = new HashSet<>();
        for (int i = bitWidth - 1; i >= 0; i--) {
            if (oneBitCounts[i] >= zeroBitCounts[i]) {
                // If there are more ONEs than zeros, keep elements with a 1
                for (long value : oxygenGeneratorCandidates) {
                    if ((value & (1L << i)) > 0) {
                        toKeep.add(value);
                    }
                }
            } else {
                // If there are more ZEROs than ones, keep elements with a 0
                for (long value : oxygenGeneratorCandidates) {
                    if ((value & (1L << i)) == 0) {
                        toKeep.add(value);
                    }
                }
            }
            // Now that we've decided what to keep, switch the toKeep list into the candidatess
            temp = oxygenGeneratorCandidates;
            oxygenGeneratorCandidates = toKeep;
            toKeep = temp;
            toKeep.clear();
            // And recount the bits
            doBitCounts(oxygenGeneratorCandidates);

            // If there's only one candidate left, get it and then stop
            if (oxygenGeneratorCandidates.size() == 1) {
                oxygenGeneratorRating = oxygenGeneratorCandidates.iterator().next();
            }
        }

        /**
         * To find CO2 scrubber rating, determine the least common value (0 or 1) in the current bit position,
         * and keep only numbers with that bit in that position.
         * If 0 and 1 are equally common, keep values with a 0 in the position being considered.
         * **/
        Set<Long> co2ScrubberCandidates = new HashSet<>(inputList);
        doBitCounts(co2ScrubberCandidates); // refresh the bit counts
        for (int i = bitWidth - 1; i >= 0; i--) {
            if (oneBitCounts[i] >= zeroBitCounts[i]) {
                // If there are more ONEs than zeros, keep elements with a 0
                for (long value : co2ScrubberCandidates) {
                    if ((value & (1L << i)) == 0) {
                        toKeep.add(value);
                    }
                }
            } else {
                // If there are more ZEROs than ones, keep elements with a 1
                for (long value : co2ScrubberCandidates) {
                    if ((value & (1L << i)) > 0) {
                        toKeep.add(value);
                    }
                }
            }
            // Now that we've decided what to keep, switch the toKeep list into the candidatess
            temp = co2ScrubberCandidates;
            co2ScrubberCandidates = toKeep;
            toKeep = temp;
            toKeep.clear();
            // And recount the bits
            doBitCounts(co2ScrubberCandidates);

            // If there's only one candidate left, get it and then stop
            if (co2ScrubberCandidates.size() == 1) {
                co2ScrubberRating = co2ScrubberCandidates.iterator().next();
            }
        }


        System.out.println(MessageFormat
                .format("oxygen generator rating:\t{0}\tCO2 Scrubber Rating:\t{1}",
                        oxygenGeneratorRating, co2ScrubberRating));
        long result = oxygenGeneratorRating * co2ScrubberRating;

        System.out.println("Day 3B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (bitWidth == null) {
                    // width of the gamma & epsilon numbers
                    // is the length of a line in the input
                    bitWidth = line.length();
                }
                inputList.add(Long.parseLong(line, 2));
            }
            // After we're done reading, count up the bits in each position
            doBitCounts(inputList);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    private void doBitCounts(
            Collection<Long> values) {        // Iterate over all the lines of the input
        oneBitCounts = new int[bitWidth];
        zeroBitCounts = new int[bitWidth];
        for (Long rate : values) {
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
    }
}
