package org.jgoeres.adventofcode2021.Day17;

import org.jgoeres.adventofcode.common.Area;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17Service {
    public boolean DEBUG = false;

    private ArrayList<Integer> inputList = new ArrayList<>();
    private Area target;

    public Day17Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day17Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 17A ===");

        long result = 0;
        /**
         * Find the initial velocity that causes the probe to reach the highest y position
         * and still eventually be within the target area after any step.
         *
         * What is the highest y position it reaches on this trajectory?
         **/

        System.out.println("Day 17A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 17B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 17B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            Pattern p =
                    Pattern.compile("target area: x=(\\d+)\\.\\.(\\d+), y=(-?\\d+)\\.\\.(-?\\d+)");
            final String line = br.readLine();  // only one line in this puzzle
            // process the line.
            Matcher m = p.matcher(line);
            if (m.find()) { // If our regex matched this line
                // Parse it
                Integer x1 = Integer.parseInt(m.group(1));
                Integer x2 = Integer.parseInt(m.group(2));
                Integer y1 = Integer.parseInt(m.group(3));
                Integer y2 = Integer.parseInt(m.group(4));
                // 1 & 2 are flipped because the input is laid out oddly
                target = new Area(new XYPoint(x1, y2), new XYPoint(x2, y1));
            }

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
