package org.jgoeres.adventofcode2021.Day15;

import org.jgoeres.adventofcode.common.Direction8Way;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15Service {
    public boolean DEBUG = false;

    private final CaveRiskMap caveRisks = new CaveRiskMap();
    //    private Map<XYPoint, Integer> totalRisk = new HashMap();
    private Integer xSize, ySize;
    private Integer blocksChecked = 0;
    private final Set<XYPoint> visited = new HashSet<>();
    private static final List<Direction8Way> neighborDirections =
            List.of(Direction8Way.RIGHT, Direction8Way.DOWN,
                    Direction8Way.LEFT, Direction8Way.UP);

    public Day15Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day15Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 15A ===");

        /**
         * What is the lowest total risk of any path from the top left to the bottom right?
         **/

        final XYPoint start = new XYPoint(0, 0);  // Start in upper left
//        totalRisk.put(start, 0);   // total risk at starting point is 0 by definition
        final XYPoint target = new XYPoint(xSize - 1, ySize - 1); // End in lower right

        final Integer targetTotalRisk = calculateTotalRisk(start, target);

        System.out.println("Total blocks checked: " + blocksChecked);
        // When we're done, the lowest total risk will be the value for target
        long result = targetTotalRisk;
        System.out.println("Day 15A: Answer = " + result);
        return result;
    }

    private Integer calculateTotalRisk(final XYPoint start, final XYPoint target) {
        final PriorityQueue<Cave> riskQueue = getRiskPriorityQueue();
        // Start at 0,0
        // Put it on the priority queue
        final Cave startCave = new Cave(start, 0); // start cave has 0 risk by definition
        riskQueue.add(startCave);
        visited.add(startCave.getXy());

        // Take the top point off the priority queue
        while (!riskQueue.isEmpty()) {
            final Cave currentCave = riskQueue.poll();
            blocksChecked++;
            // Otherwise...
            // Calculate the total risk of each of its neighbors
            for (Direction8Way direction : neighborDirections) {
                final XYPoint neighborXY = currentCave.getXy().getRelativeLocation(direction);
                if (caveRisks.containsKey(neighborXY)) {
                    if (!visited.contains(neighborXY)) {
                        visited.add(neighborXY);
                        // Insert them in a priority queue based on LOWEST total risk
                        final Cave neighborCave = new Cave(neighborXY,
                                currentCave.getTotalRisk() + caveRisks.get(neighborXY));
                        // If it's the target point, we're done!
                        if (neighborCave.getXy().equals(target)) {
                            return neighborCave.getTotalRisk();
                        } else {
                            riskQueue.add(neighborCave);
                        }
                    }
                }
            }
            // Process the next item in the top of the queue
        }
        return 0;   // we should never get here
    }

    private PriorityQueue<Cave> getRiskPriorityQueue() {
        return new PriorityQueue<>(new CaveReverseRiskComparator());
    }

    public long doPartB() {
        System.out.println("=== DAY 15B ===");

        /**
         * What is the lowest total risk of any path from the top left to the bottom right
         * if the grid is expanded by a factor of 5 each way, with the special rules
         * for the risk at each point?
         **/

        final Integer factor = 5;
        final XYPoint start = new XYPoint(0, 0);  // Start in upper left
        final XYPoint target =
                new XYPoint(xSize * factor - 1, ySize * factor - 1); // End in lower right
        caveRisks.setFactor(factor);
//        printCaveRisk(factor);
        final Integer targetTotalRisk = calculateTotalRisk(start, target);

        System.out.println("Total blocks checked: " + blocksChecked);
        // When we're done, the lowest total risk will be the value for target
        long result = targetTotalRisk;
        System.out.println("Day 15B: Answer = " + result);
        return result;
    }

    private void  printCaveRisk(Integer factor) {
        for (int y = 0; y < caveRisks.getySize(); y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < caveRisks.getxSize(); x++) {
                sb.append(caveRisks.get(new XYPoint(x,y)));
            }
            System.out.println(sb);
        }
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        caveRisks.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            final Pattern p = Pattern.compile("\\d");
            Integer y = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                Integer x = 0;
                final Matcher m = p.matcher(line);
                while (m.find()) { // If our regex matched this line
                    // Parse it
                    final Integer risk = Integer.parseInt(m.group(0));
                    caveRisks.put(new XYPoint(x, y), risk);
                    x++;
                }
                y++;
                xSize = x;
            }
            ySize = y;
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
