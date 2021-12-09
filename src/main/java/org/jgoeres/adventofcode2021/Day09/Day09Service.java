package org.jgoeres.adventofcode2021.Day09;

import org.jgoeres.adventofcode.common.Direction8Way;
import org.jgoeres.adventofcode.common.DirectionNESW;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09Service {
    public boolean DEBUG = false;

    private Map<XYPoint, Integer> heightMap = new HashMap<>();
    private Integer xSize;
    private Integer ySize;

    public Day09Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day09Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 9A ===");

        long totalRisk = 0;
        /**
         * Find all of the low points on your heightmap.
         * What is the sum of the risk levels of all low points on your heightmap?
         * **/
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                XYPoint xy = new XYPoint(x, y);
                if (lowerThanNeighbors(xy, heightMap)) {
                    totalRisk += riskLevel(heightMap.get(xy));
                }
            }
        }

        System.out.println("Day 9A: Answer = " + totalRisk);
        return totalRisk;
    }

    public long doPartB() {
        System.out.println("=== DAY 9B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 9B: Answer = " + result);
        return result;
    }

    private boolean lowerThanNeighbors(XYPoint xy, Map<XYPoint, Integer> heightMap) {
        // Check all four neighbors (if they exist) and see if we're lower than all of them
        return (lowerThan(xy, heightMap, Direction8Way.UP)
                && lowerThan(xy, heightMap, Direction8Way.RIGHT)
                && lowerThan(xy, heightMap, Direction8Way.DOWN)
                && lowerThan(xy, heightMap, Direction8Way.LEFT)
        );
    }

    private boolean lowerThan(XYPoint xy, Map<XYPoint, Integer> heightMap,
                              Direction8Way direction) {
        XYPoint neighbor = xy.getRelativeLocation(direction);
        // If the neighbor exists, see if we're lower
        // If it doesn't (we're on an edge/corner) just return true (like the edge is infinite)
        return (heightMap.containsKey(neighbor)) ?
                (heightMap.get(xy) < heightMap.get(neighbor)) : true;
    }

    private Integer riskLevel(Integer height) {
        return height + 1;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        heightMap.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            /** Replace this regex **/
            Pattern p = Pattern.compile("(\\d)");

            int j = 0; // vertical
            while ((line = br.readLine()) != null) {
                // process the line.
                int i = 0; // horizontal
                Matcher m = p.matcher(line);
                while (m.find()) { // If our regex matched this line
                    // Parse it
                    Integer height = Integer.parseInt(m.group(1));
                    heightMap.put(new XYPoint(i, j), height);
                    i++;
                    // Store the extents of the heightMap
                    xSize = i;
                }
                j++;
                ySize = j;
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
