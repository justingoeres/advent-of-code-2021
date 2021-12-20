package org.jgoeres.adventofcode2021.Day20;

import org.jgoeres.adventofcode.common.Utils.Pair;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20Service {
    public boolean DEBUG = false;

    private ArrayList<Integer> inputList = new ArrayList<>();
    private static final Character PIXEL_ON = '#';
    private static final Character PIXEL_OFF = '.';

    private final Set<Integer> pixelOnIndex = new HashSet<>();
    private final Set<XYPoint> pixelMap = new HashSet<>();
    private final Set<XYPoint> nextPixelMap = new HashSet<>();
    private final Pair<XYPoint> pixelMapSwap = new Pair(pixelMap, nextPixelMap);

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
        /** Put problem implementation here **/

        System.out.println("Day 20A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 20B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 20B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            /** Replace this regex **/
            Pattern p = Pattern.compile("([FB]{7})([LR]{3})");
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
                        .forEach(c -> pixelMap.add(new XYPoint(finalRow, c)));
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
