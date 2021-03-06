package org.jgoeres.adventofcode2021.Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12Service {
    public boolean DEBUG = false;

    private final Map<String, Cave> caves = new HashMap<>();

    public Day12Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day12Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 12A ===");

        long result = 0;
        /**
         * How many paths through this cave system
         * are there that visit small caves at most once?
         **/

        // Start at the beginning
        Journey journey = new Journey(caves.get("start"), caves);

        // For each cave we can reach from here, spawn a journey to go there
        journey.findTheEndPartA();
        result = journey.getPathsFound();
        System.out.println("Day 12A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 12B ===");

        long result = 0;
        /**
         * big caves can be visited any number of times,
         * a single small cave can be visited at most twice,
         * and the remaining small caves can be visited at most once.
         *
         * However, the caves named start and end can only be visited exactly once each:
         * once you leave the start cave, you may not return to it,
         * and once you reach the end cave, the path must end immediately.
         *
         * Given these new rules, how many paths through this cave system are there?
         **/

        // Start at the beginning
        Journey journey = new Journey(caves.get("start"), caves);

        journey.findTheEndPartB();
        result = journey.getPathsFound();
        System.out.println("Day 12B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        caves.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            /**
             * e.g.
             * dc-end
             * HN-start
             * start-kj
             * dc-start
             * dc-HN
             * **/
            final Pattern p = Pattern.compile("([A-Za-z]+)-([A-Za-z]+)");
            while ((line = br.readLine()) != null) {
                // process the line.
                final Matcher m = p.matcher(line);
                if (m.find()) { // If our regex matched this line
                    // Create/update the two caves
                    // Set them as neighbors of each other
                    final String cave1Name = m.group(1);
                    final String cave2Name = m.group(2);

                    final Cave cave1 = caves.getOrDefault(cave1Name, new Cave(cave1Name));
                    final Cave cave2 = caves.getOrDefault(cave2Name, new Cave(cave2Name));

                    cave1.addBigOrSmallNeighbor(cave2);
                    cave2.addBigOrSmallNeighbor(cave1);

                    caves.put(cave1Name, cave1);
                    caves.put(cave2Name, cave2);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
