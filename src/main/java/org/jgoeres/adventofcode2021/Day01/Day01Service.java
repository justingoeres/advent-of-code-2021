package org.jgoeres.adventofcode2021.Day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day01Service {
    public boolean DEBUG = false;

    private ArrayList<Integer> inputList = new ArrayList<>();

    public Day01Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day01Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 01A ===");

        long result = 0;
        long previous = inputList.get(0);
        /** count the number of times a depth measurement increases from the previous measurement. **/
        for (int i = 1; i < inputList.size(); i++) {
            // compare depth[i] to the previous depth[i-1]
            long current = inputList.get(i);
            if (current > previous) {
                result++;
            }
            previous = current;
        }
        System.out.println("Day 01A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 01B ===");

        long result = 0;
        /** Consider sums of a three-measurement sliding window. How many sums are larger than the previous sum?
         **/

        // Two sliding three-measurement windows offset by one have two values in common:
        //      A B C
        //        B C D
        // so A + B + C > B + C + D if A > D. That's all we have to check
        long previous = inputList.get(0);
        /** count the number of times a depth measurement increases from the previous measurement. **/
        for (int i = 3; i < inputList.size(); i++) {
            // compare depth[i] to the previous depth[i-1]
            long current = inputList.get(i);
            if (current > previous) {
                result++;
            }
            previous = inputList.get(i - 2);
        }
        System.out.println("Day 01B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            /** Replace this regex **/
            Pattern p = Pattern.compile("([FB]{7})([LR]{3})");
            while ((line = br.readLine()) != null) {
                // process the line.
                Integer depth = Integer.parseInt(line);
                inputList.add(depth);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
