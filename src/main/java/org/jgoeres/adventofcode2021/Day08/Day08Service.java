package org.jgoeres.adventofcode2021.Day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08Service {
    public boolean DEBUG = false;

    private ArrayList<Display> inputList = new ArrayList<>();

    public Day08Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day08Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 8A ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 8A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 8B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 8B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            /** Each entry consists of ten unique signal patterns,
             * a | delimiter, and finally the four digit output value **/
            Pattern signalPattern = Pattern.compile("([a-g]+)\\s?");
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split("\\|");
                // process the first section (the patterns)
                Matcher m = signalPattern.matcher(splitLine[0]);
                List<String> patterns = new ArrayList<>();
                while (m.find()) { // If our regex matched this line
                    // Parse it
                    patterns.add(m.group(1));
                }
                m = signalPattern.matcher(splitLine[1]);
                List<String> digits = new ArrayList<>();
                while (m.find()) {
                    digits.add(m.group(1));
                }
                inputList.add(new Display(patterns, digits));
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
