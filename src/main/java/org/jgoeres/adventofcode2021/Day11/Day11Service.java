package org.jgoeres.adventofcode2021.Day11;

import org.jgoeres.adventofcode.common.Direction8Way;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11Service {
    public boolean DEBUG = false;

    private HashMap<XYPoint, Integer> octopodEnergy = new HashMap<>();
    public static final int TOTAL_STEPS = 100;

    public Day11Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day11Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 11A ===");

        long result = 0;
        /** During a single step, the following occurs:

         - First, the energy level of each octopus increases by 1.
         - Then, any octopus with an energy level greater than 9 flashes.
         This increases the energy level of all adjacent octopuses by 1,
         including octopuses that are diagonally adjacent. If this causes
         an octopus to have an energy level greater than 9, it also flashes.
         This process continues as long as new octopuses keep having their energy
         level increased beyond 9. (An octopus can only flash at most once per step.)
         - Finally, any octopus that flashed during this step has its energy
         level set to 0, as it used all of its energy to flash.
         **/
//        System.out.println(" == STARTING CONFIGURATION");
//        printOctopods();
//        System.out.println();
        long count = 0;
        for (int step = 0; step < TOTAL_STEPS; step++) {
            count += doTimerTick();
//            System.out.println("== After Step " + (step + 1) + ":");
//            printOctopods();
//            System.out.println(); // blank line to separate
        }

        System.out.println("Day 11A: Answer = " + count);
        return count;
    }

    private long doTimerTick() {
        // Bump all octopus energy by 1
        octopodEnergy
                .forEach((octopus, energyLevel) -> {
                    octopodEnergy.compute(octopus, (key, value) -> value + 1);
                });
        // Find all octopodes with an energy level of EXACTLY 10
        // (because these are ready to flash but haven't yet)
        // then iterate over them to propagate their flash
        List<XYPoint> readyToFlash =
                octopodEnergy.entrySet().stream()
                        .filter(p -> p.getValue() == 10)
                        .map(octopodEnergy -> octopodEnergy.getKey()).collect(Collectors.toList());
        // For each octopode that is ready to flash, flash it and propagate from there
        readyToFlash.stream().forEach(octopus -> propagateFlash(octopus, octopodEnergy));

        // After the flashes are all propagated, add up the total (which is all octopods with energy >= 10)
        List<XYPoint>
                flashes = octopodEnergy.entrySet().stream().filter(p -> p.getValue() >= 10)
                .map(entry -> entry.getKey()).collect(Collectors.toList());
        final long count = flashes.size();
        // Then set them all to zero energy
        flashes.forEach(octopus -> {
            octopodEnergy.compute(octopus, (key, value) -> 0);
        });
//        System.out.println("Flashes:\t" + count);
        return count;
    }

    private void propagateFlash(XYPoint xy, HashMap<XYPoint, Integer> octopodEnergy) {
        // Get all the neighbors of this octopod and bump their energy by 1
        Set<XYPoint> neighbors = getNeighbors(xy, octopodEnergy);
        neighbors.forEach(octopus -> octopodEnergy.compute(octopus, (key, value) -> value + 1));
        // Then, for any that are at EXACTLY an energy of 10, propagate THEIR flash

//        neighbors.stream().filter(p -> octopodEnergy.get(p).equals(10))
//                .forEach(neighbor -> propagateFlash(neighbor, octopodEnergy));

        List<XYPoint> nextFlashes = neighbors.stream().filter(p -> octopodEnergy.get(p).equals(10))
                .collect(Collectors.toList());
        for (XYPoint nextFlash : nextFlashes) {
            propagateFlash(nextFlash, octopodEnergy);
        }

    }

    private Set<XYPoint> getNeighbors(XYPoint xy, HashMap<XYPoint, Integer> octopodEnergy) {
        // Find all of the eight neighbors of xy that exist in the map
        Set<XYPoint> neighbors = Arrays.stream(Direction8Way.values())
                .filter(direction -> octopodEnergy.containsKey(
                        xy.getRelativeLocation8Way(direction)))
                .map(direction -> xy.getRelativeLocation8Way(direction))
                .collect(Collectors.toSet());
        return neighbors;
    }

    private void printOctopods() {
        int size = (int) Math.sqrt(octopodEnergy.size());
        for (int row = 0; row < size; row++) {
            StringBuilder outputLine = new StringBuilder();
            for (int col = 0; col < size; col++) {
                // Print this line
                int energy = octopodEnergy.get(new XYPoint(row, col));
                outputLine.append(energy)
                        .append(" ");
            }
            System.out.println(outputLine);
        }
        System.out.println(); // blank line after
    }


    public long doPartB() {
        System.out.println("=== DAY 11B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 11B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        octopodEnergy.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            /** Each digit is one octopus's energy level **/
            Pattern p = Pattern.compile("\\d");
            Integer row = 0;
            while ((line = br.readLine()) != null) {
                Integer column = 0;
                // process the line.
                final Matcher m = p.matcher(line);
                while (m.find()) { // If there are still more octopodes to create
                    octopodEnergy.put(new XYPoint(row, column), Integer.parseInt(m.group(0)));
                    column++;
                }
                row++;
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
