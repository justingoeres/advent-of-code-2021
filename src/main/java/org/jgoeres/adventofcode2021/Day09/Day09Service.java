package org.jgoeres.adventofcode2021.Day09;

import org.jgoeres.adventofcode.common.Direction8Way;
import org.jgoeres.adventofcode.common.DirectionNESW;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09Service {
    public boolean DEBUG = false;

    private Map<XYPoint, Integer> heightMap = new HashMap<>();
    private Integer xSize;
    private Integer ySize;
    private Set<XYPoint> lowPoints = new HashSet<>();

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
                    lowPoints.add(xy);
                    totalRisk += riskLevel(heightMap.get(xy));
                }
            }
        }

        System.out.println("Day 9A: Answer = " + totalRisk);
        return totalRisk;
    }

    public long doPartB() {
        System.out.println("=== DAY 9B ===");

        /** What do you get if you multiply together the sizes of the three largest basins? **/

        // Iterate through all the lowest points. For each one, find all the connected
        // xy points that have height of < 9. For each one, add one to the size counter for the basin

        // We should be able to solve this recursively.
        // 1. Init the basinSize counter to 0.
        // 2. Start at point xy (the low point)
        // 3. Remove this point from the heightMap (so we don't check it twice)
        // 4. Increment the basinSize counter
        // 5. For each existing neighbor from HeightMap whose height is < 9...
        // 6. repeat from 3.
        // 7. when we finish, add the size of this basin to the TreeSet of all basins
        List<Integer> basinSizes = new ArrayList<>();
        // For all the lowPoints
        for (XYPoint lowPoint : lowPoints) {
            // 1. Init the basinSize counter to 0.
            Integer basinSize = 0;
            // 2. Start at point xy (the low point)
//            System.out.println(MessageFormat
//                    .format("--- Starting from low point ({0}, {1})",
//                            lowPoint.getX(), lowPoint.getY()));
            basinSize = findBasinSize(lowPoint, basinSize);

            basinSizes.add(basinSize);
        }
        Collections.sort(basinSizes);
        Collections.reverse(basinSizes);
        // Once we've calculated all the sizes, find the three largest and multiply them
        long result = 1;
        for (int i = 0; i < 3; i++) {
            result *= basinSizes.get(i);
        }

        System.out.println("Day 9B: Answer = " + result);
        return result;
    }

    private Integer findBasinSize(XYPoint xy, Integer currentSize) {
        // 3. Remove this point from the heightMap (so we don't check it twice)
        currentSize++;
//        System.out.println(MessageFormat
//                .format("Checking point ({0}, {1})\theight:\t{2}\tsize:\t{3}", xy.getX(), xy.getY(),
//                        heightMap.get(xy), currentSize));
        heightMap.remove(xy);
        final Set<Direction8Way> neighborDirections =
                Set.of(Direction8Way.UP, Direction8Way.RIGHT, Direction8Way.DOWN,
                        Direction8Way.LEFT);

        // 4. Increment the basinSize counter
        for (Direction8Way direction : neighborDirections) {
            // 5. For each existing neighbor from HeightMap whose height is < 9...
            XYPoint neighbor = xy.getRelativeLocation(direction);
            if (heightMap.containsKey(neighbor)
                    && heightMap.get(neighbor) < 9) {
                currentSize = findBasinSize(neighbor, currentSize);
            }
        }
        // If we've checked all the neighbors, return the size up the stack
        return currentSize++;
        // 6. repeat from 3.
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
