package org.jgoeres.adventofcode2021.Day20;

import static org.jgoeres.adventofcode.common.Direction8Way.*;
import static org.jgoeres.adventofcode2021.Day20.PixelMap.PIXEL_ON;
import org.jgoeres.adventofcode.common.Direction8Way;
import org.jgoeres.adventofcode.common.Utils.Pair;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day20Service {
    public boolean DEBUG = false;

    private ArrayList<Integer> inputList = new ArrayList<>();

    private final Set<Integer> pixelOnIndex = new HashSet<>();
    private PixelMap pixelMap = new PixelMap();
    private PixelMap nextPixelMap = new PixelMap();
    private final Pair<PixelMap> pixelMapSwap = new Pair(pixelMap, nextPixelMap);

    private Integer tickCount = 0;

    public Day20Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day20Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 20A ===");

        long result = 0;
        /**
         * Start with the original input image and apply the image enhancement algorithm twice,
         * being careful to account for the infinite size of the images.
         *
         * How many pixels are lit in the resulting image?
         **/
        int i;
        for (i = tickCount; i < 2; i++) {
            doTick(i);
//            System.out.println("== After " + (i + 1) + " ticks ==\n"
//                    + pixelMap.print() + "\n");
        }
        tickCount = i;

        result = pixelMap.getMap().size();
        System.out.println("Day 20A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 20B ===");

        long result = 0;
        /** How many pixels are lit after *50* ticks? **/
        for (int i = tickCount; i < 50; i++) {
            doTick(i);
//            System.out.println("== After " + (i + 1) + " ticks ==\n"
//                    + pixelMap.print() + "\n");
        }

        result = pixelMap.getMap().size();
        System.out.println("Day 20B: Answer = " + result);
        return result;
    }

    private void doTick(Integer count) {
        // The first thing we need to do is figure out which pixels we care about.
        // Every pixel that has at least one neighbor that is ON might need to turn on.
        // Therefore we need to check all of our ON pixels PLUS their 8 neighbors.
        //
        // However, because our pixelMap has a # (turn ON) for pixels with NO neighbors (index 0)
        // we need a way to account for the fact that the entire infinite grid is going to flash
        // on and off on every tick(!?) -- PLUS any "orphans" in the middle of the grid with no neighbors
        // will also turn on!
        //
        // That means that at the very least we need to check the whole (known) grid every tick
        // However, we can calculate the state of the "edge" pixels based on whether our tick count
        // is odd or even.

        // First, extend the "edge" of our search area by 1 in X & Y
        Integer newXMin = pixelMap.getxMin() - 1;
        Integer newXMax = pixelMap.getxMax() + 1;
        Integer newYMin = pixelMap.getyMin() - 1;
        Integer newYMax = pixelMap.getyMax() + 1;
        pixelMap.setExtents(newXMin, newXMax, newYMin, newYMax);

        // Now check the whole search area and use it to determine nextPixelMap
        nextPixelMap.clear();
        final XYPoint toCheck = new XYPoint();  // avoid creating thousands of XYPoints
        for (int y = newYMin; y <= newYMax; y++) {
            for (int x = newXMin; x <= newXMax; x++) {
                // For each pixel
                toCheck.set(x, y);
                // Check all its neighbors to determine its 'value'
                Integer value = getPixelValue(toCheck, pixelMap, count);
                // If a pixel of this value is supposed to be ON, add it to the next map
                if (pixelOnIndex.contains(value)) {
                    nextPixelMap.add(new XYPoint(x, y));
                }
            }
        }
        // swap the maps
        pixelMapSwap.swap();
        pixelMap = pixelMapSwap.getFirst();
        nextPixelMap = pixelMapSwap.getSecond();
    }

    private Integer getPixelValue(XYPoint xy, PixelMap pixelMap, Integer tickCount) {
        Integer value = 0;
        for (Optional<Direction8Way> neighbor : neighbors) {
            value <<= 1;    // shift value, THEN add
            XYPoint neighborXY =
                    (neighbor.isPresent()) ? xy.getRelativeLocation8Way(neighbor.get()) : xy;

            // A neighbor is ON if the pixelMap contains it - simple!
            // However! A pixel is ALSO on if
            //  * our pixelOnIndex has a '#' in position 0, and
            //  * this pixel was beyond the edge of the known grid last tick, and
            //  * the tick count is... odd?
            Boolean isOn = false;
            if ((tickCount % 2 != 0) && pixelOnIndex.contains(0)) {
                if ((neighborXY.getX() <= pixelMap.getxMin())
                        || (neighborXY.getX() >= pixelMap.getxMax())
                        || (neighborXY.getY() <= pixelMap.getyMin())
                        || (neighborXY.getY() >= pixelMap.getyMax())) {
                    isOn = true;
                } else {
                    isOn = pixelMap.contains(neighborXY);
                }
            } else {
                isOn = pixelMap.contains(neighborXY);
            }
            value += isOn ? 1 : 0;
        }
        return value;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            final String firstLine = br.readLine();  // first line is our pixel index
            // Add an item to our index of value -> PIXEL_ON for each '#'
            IntStream.range(0, firstLine.length())
                    .filter(c -> Character.valueOf(firstLine.charAt(c)).equals(PIXEL_ON))
                    .mapToObj(Integer::valueOf).forEach(c -> pixelOnIndex.add(c));
            br.readLine();  // skip the blank line
            Integer row = 0;
            while ((line = br.readLine()) != null) {
                // process the pixel map
                // E.g.     .##..##...#...#...# ...
                String finalLine = line;
                Integer finalRow = row;
                // Add an item to our set of ON pixels for each '#'
                IntStream.range(0, line.length())
                        .filter(c -> Character.valueOf(finalLine.charAt(c)).equals(PIXEL_ON))
                        .mapToObj(Integer::valueOf)
                        .forEach(c -> pixelMap.add(new XYPoint(c, finalRow)));
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final List<Optional<Direction8Way>> neighbors = List.of(
            Optional.of(UP_LEFT),
            Optional.of(UP),
            Optional.of(UP_RIGHT),
            Optional.of(LEFT),
            Optional.empty(),   // self
            Optional.of(RIGHT),
            Optional.of(DOWN_LEFT),
            Optional.of(DOWN),
            Optional.of(DOWN_RIGHT)
    );
}
