package org.jgoeres.adventofcode2021.Day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07Service {
    public boolean DEBUG = false;

    private ArrayList<Integer> inputList = new ArrayList<>();

    public Day07Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day07Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 7A ===");

        long result = 0;
        /** Determine the horizontal position that the crabs can align to
         * using the least fuel possible. How much fuel must they spend
         * to align to that position?
         **/

        Integer fuelTotal = 0;
        final Integer finalCrab = inputList.size() - 1;
        for (int i = 0; i < inputList.size() / 2; i++) {
            Integer start = inputList.get(i);
            Integer end = inputList.get(finalCrab - i);
            Integer distance = end - start;
            fuelTotal += distance;
        }
        System.out.println("Day 7A: Answer = " + fuelTotal);
        return fuelTotal;
    }

    public long doPartB() {
        System.out.println("=== DAY 7B ===");

        long result = 0;
        /** Determine the horizontal position that the crabs can align to
         *  using the least fuel possible so they can make you an escape route!
         *  How much fuel must they spend to align to that position? **/

        // The final position is the midpoint between the two "center" crabs
//        Integer targetLeft = (inputList.size() - 1) / 2;
//        Integer target = (inputList.get(targetLeft) + inputList.get(targetLeft + 1)) / 2;

        // The final position when accounting for fuel is the mean of all points?
        Float target = (inputList.stream().reduce(0,Integer::sum)).floatValue() / inputList.size();
        Integer intTarget = Math.round(target);

        Integer totalFuel = 0;
        for (Integer crab: inputList) {
            totalFuel += calculateFuel(crab, intTarget);
        }

        System.out.println("Day 7B: Answer = " + totalFuel);
        return totalFuel;
    }

    private Integer calculateFuel(final Integer current, final Integer target) {
        Integer distance = Math.abs(target - current);

        Integer fuelRequired = 0;
        for (int i = 1; i <= distance; i++) {
            fuelRequired += i;
        }
        return fuelRequired;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            // e.g. 3,4,3,1,2
            final Pattern p = Pattern.compile("(\\d+)");
            line = br.readLine();   // only one line in this input
            // process the line.
            final Matcher m = p.matcher(line);
            while (m.find()) { // while there are more Integers to match
                // Parse each one
                inputList.add(Integer.parseInt(m.group(1)));
            }
            Collections.sort(inputList);    // sort it to make the problem easy
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
