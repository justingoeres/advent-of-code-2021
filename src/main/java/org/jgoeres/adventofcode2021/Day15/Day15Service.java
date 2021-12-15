package org.jgoeres.adventofcode2021.Day15;

import org.jgoeres.adventofcode.common.Direction8Way;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15Service {
    public boolean DEBUG = false;

    private Map<XYPoint, Integer> cave = new HashMap();
    private Map<XYPoint, Integer> totalRisk = new HashMap();
    private Integer xSize, ySize;

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

        calculateTotalRisks(start, target);

        // When we're done, the lowest total risk will be the value for target
        long result = totalRisk.get(target);
        System.out.println("Day 15A: Answer = " + result);
        return result;
    }

    private void calculateTotalRisks(final XYPoint xy, final XYPoint target) {
        final List<Direction8Way> neighborDirections =
                List.of(Direction8Way.RIGHT, Direction8Way.DOWN,
                        Direction8Way.LEFT, Direction8Way.UP);
        Integer currentTotalRisk = totalRisk.get(xy);   // guaranteed to exist

        // If we've already exceeded the known totalRisk of getting to the target, just abort now
        if (currentTotalRisk >= totalRisk.getOrDefault(target, Integer.MAX_VALUE)) {
            return;
        }

        // Start at the current point (we know its totalRisk already)
        for (Direction8Way direction : neighborDirections) {
            // Get all its neighbors
            XYPoint neighbor = xy.getRelativeLocation(direction);
            if (cave.containsKey(neighbor)) {
                Integer newTotalRisk = currentTotalRisk + cave.get(neighbor);
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

    public long doPartB() {
        System.out.println("=== DAY 15B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 15B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        cave.clear();
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
                    cave.put(new XYPoint(x, y), risk);
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
