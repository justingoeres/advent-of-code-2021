package org.jgoeres.adventofcode2021.Day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

        // Pick a candidate target point. Start with the mean of all points
        // (will truncate to integer but that's OK)
        Integer target = (inputList.stream().reduce(0, Integer::sum)) / inputList.size();

        while (true) {
            // go until we find a solution
            // Find the fuel requirements to get to THIS spot
            Integer fuelForTarget = calculateAllFuel(inputList, target);
            // Find the fuel requirements to get to the spot on either side of this spot
            Integer fuelLeft = calculateAllFuel(inputList, target - 1);
            Integer fuelRight = calculateAllFuel(inputList, target + 1);

            System.out.print(MessageFormat
                    .format("target = {0}\tFuel required:\t(left) {1}   {2}   {3} (right)\t",
                            target, fuelLeft, fuelForTarget, fuelRight));
            if (fuelLeft >= fuelForTarget && fuelForTarget <= fuelRight) {
                // If this is a local minimum, we're done!
                System.out.println("FOUND A MINIMUM!");
                System.out.println("Day 7B: Answer = " + fuelForTarget);
                return fuelForTarget;
            } else if (fuelForTarget > fuelLeft) {
                // otherwise, move toward the LOWER target next to us
                System.out.println("Moving LEFT");
                target--;
            } else if (fuelForTarget > fuelRight) {
                System.out.println("Moving RIGHT");
                target++;
            }
        }
    }

    private Integer calculateAllFuel(List<Integer> crabs, final Integer target) {
        Integer fuelRequired = 0;
        for (Integer crab : crabs) {
            fuelRequired += calculateFuel(crab, target);
        }
        return fuelRequired;
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
