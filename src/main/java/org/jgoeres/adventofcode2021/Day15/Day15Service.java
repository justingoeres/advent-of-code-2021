package org.jgoeres.adventofcode2021.Day15;

import org.jgoeres.adventofcode.common.Direction8Way;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15Service {
    public boolean DEBUG = false;

    private Map<XYPoint, Integer> caveRisks = new HashMap();
    private Map<XYPoint, Integer> totalRisk = new HashMap();
    private Integer xSize, ySize;
    private Integer blocksChecked = 0;
    private Map<XYPoint, Cave> knownRisks = new HashMap<>();
    private Set<XYPoint> visited = new HashSet<>();
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

        XYPoint start = new XYPoint(0, 0);  // Start in upper left
        totalRisk.put(start, 0);   // total risk at starting point is 0 by definition
        XYPoint target = new XYPoint(xSize - 1, ySize - 1); // End in lower right

//        calculateTotalRisks(start, target);
        Integer targetTotalRisk = calculateTotalRisksPriorityQueue(start, target);

        System.out.println("Total blocks checked: " + blocksChecked);
        // When we're done, the lowest total risk will be the value for target
        long result = targetTotalRisk;
        System.out.println("Day 15A: Answer = " + result);
        return result;
    }

    private void calculateTotalRisks(final XYPoint xy, final XYPoint target) {
        Integer currentTotalRisk = totalRisk.get(xy);   // guaranteed to exist
        blocksChecked++;
        // If we've already exceeded the known totalRisk of getting to the target, just abort now
        if (currentTotalRisk >= totalRisk.getOrDefault(target, Integer.MAX_VALUE)) {
            return;
        }

        // Start at the current point (we know its totalRisk already)
        for (Direction8Way direction : neighborDirections) {
            // Get all its neighbors
            XYPoint neighbor = xy.getRelativeLocation(direction);
            if (caveRisks.containsKey(neighbor)) {
                Integer newTotalRisk = currentTotalRisk + caveRisks.get(neighbor);
                // Calculate the new totalRisk of each neighbor coming from the current point
                if (newTotalRisk < totalRisk.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    // If the new totalRisk is *less than* the previously-known totalRisk
                    //  * Replace it with our new totalRisk for that neighbor
                    //  * Process that neighbor (this is the recursion)
                    totalRisk.put(neighbor, newTotalRisk);
                    calculateTotalRisks(neighbor, target);
                }
                // If the new totalRisk is *not less than* the previously-known totalRisk
                //  * Do nothing, just return
            }
        }
    }

    private Integer calculateTotalRisksPriorityQueue(final XYPoint start, final XYPoint target) {
        PriorityQueue<Cave> riskQueue = getRiskPriorityQueue();
        // Start at 0,0
        // Put it on the priority queue
        Cave startCave = new Cave(start, 0); // start cave has 0 risk by definition
        riskQueue.add(startCave);
        visited.add(startCave.getXy());

        // Take the top point off the priority queue
        while (!riskQueue.isEmpty()) {
            Cave currentCave = riskQueue.poll();
            blocksChecked++;
            // Otherwise...
            // Calculate the total risk of each of its neighbors
            for (Direction8Way direction : neighborDirections) {
                XYPoint neighborXY = currentCave.getXy().getRelativeLocation(direction);
                if (caveRisks.containsKey(neighborXY)) {
                    if (!visited.contains(neighborXY)) {
                        visited.add(neighborXY);
                        // Insert them in a priority queue based on LOWEST total risk
                        Cave neighborCave = new Cave(neighborXY,
                                currentCave.getTotalRisk() + caveRisks.get(neighborXY));
//                        System.out.println(MessageFormat
//                                .format("New visit to: {0},{1}", neighborXY.getX(),
//                                        neighborXY.getY()));
                        // If it's the target point, we're done!
                        if (neighborCave.getXy().equals(target)) {
                            return neighborCave.getTotalRisk();
                        } else {
                            riskQueue.add(neighborCave);
                        }
                    } else {
//                        System.out.println(MessageFormat
//                                .format("Already visited: {0},{1}", neighborXY.getX(),
//                                        neighborXY.getY()));
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

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 15B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        caveRisks.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Pattern p = Pattern.compile("\\d");
            Integer y = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                Integer x = 0;
                Matcher m = p.matcher(line);
                while (m.find()) { // If our regex matched this line
                    // Parse it
                    Integer risk = Integer.parseInt(m.group(0));
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
