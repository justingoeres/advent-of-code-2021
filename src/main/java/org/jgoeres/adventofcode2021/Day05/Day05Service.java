package org.jgoeres.adventofcode2021.Day05;

import static org.jgoeres.adventofcode2021.Day05.Direction.*;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day05Service {
    public boolean DEBUG = false;

    private ArrayList<VentDefinition> inputList = new ArrayList<>();
    private Map<XYPoint, Integer> vents = new HashMap<>();

    public Day05Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day05Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 5A ===");

        /** Consider only horizontal and vertical lines.
         * At how many points do at least two lines overlap? **/

        for (VentDefinition vent : inputList) {
            if (vent.getDirection() == HORIZONTAL) {
                int x1 = Math.min(vent.getStart().getX(), vent.getEnd().getX());
                int x2 = Math.max(vent.getStart().getX(), vent.getEnd().getX());
                int y = vent.getStart().getY();
                for (int x = x1; x <= x2; x++) {
                    XYPoint ventLocation = new XYPoint(x, y);
                    if (vents.containsKey(ventLocation)) {
                        // if the vent already exists, increment it
                        vents.compute(ventLocation, (key, value) ->
                                value + 1);
                    } else {
                        // if the vent doesn't exist, add it
                        vents.put(ventLocation, 1);
                    }

                }
            } else if (vent.getDirection() == VERTICAL) {
                int y1 = Math.min(vent.getStart().getY(), vent.getEnd().getY());
                int y2 = Math.max(vent.getStart().getY(), vent.getEnd().getY());
                int x = vent.getStart().getX();
                for (int y = y1; y <= y2; y++) {
                    XYPoint ventLocation = new XYPoint(x, y);
                    if (vents.containsKey(ventLocation)) {
                        // if the vent already exists, increment it
                        vents.compute(ventLocation, (key, value) ->
                                value + 1);
                    } else {
                        // if the vent doesn't exist, add it
                        vents.put(ventLocation, 1);
                    }
                }
            }
        }
        // When we're done mapping the floor, add up all the spots with more than one vent
        long result = vents.entrySet().stream().filter(p -> p.getValue() > 1).count();

        System.out.println("Day 5A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 5B ===");

        /** Now do the same thing, but include diagonal vents **/

        // If the vents map is empty, do part A first
        if (vents.isEmpty()) {
            doPartA();
        }

        // We already have the horizontal & vertical vents mapped, so just do the diagonals now
        for (VentDefinition vent : inputList.stream().filter(p -> p.getDirection() == DIAGONAL)
                .collect(Collectors.toList())) {
            // Figure out whether the row points up-and-right or down-and-right
            // Start at the left end (minimum x)
            Integer x1 = vent.getStart().getX();
            Integer x2 = vent.getEnd().getX();
            XYPoint start, end;
            if (x1 < x2) {
                // The vent's "start" point is leftmost,
                start = vent.getStart();
                end = vent.getEnd();
            } else {
                // The vent's "end" point is leftmost
                start = vent.getEnd();
                end = vent.getStart();
            }
            // Now walk from the start (leftmost) point to the end (rightmost)
            int y = start.getY();
            int yDirection = Integer.signum(end.getY() - y);
            for (int x = start.getX(); x <= end.getX(); x++) {
                XYPoint ventLocation = new XYPoint(x, y);
                if (vents.containsKey(ventLocation)) {
                    // if the vent already exists, increment it
                    vents.compute(ventLocation, (key, value) ->
                            value + 1);
                } else {
                    // if the vent doesn't exist, add it
                    vents.put(ventLocation, 1);
                }
                // We're going to increment x, but we also need
                // to increment y because we're moving diagonally
                y += yDirection;
            }
        }
        // When we're done mapping the floor, add up all the spots with more than one vent
        long result = vents.entrySet().stream().filter(p -> p.getValue() > 1).count();
        System.out.println("Day 5B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            /** Each line of the input is one row of vents on the sea floor
             *  **/
            Pattern p = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");
            while ((line = br.readLine()) != null) {
                // process the line.
                Matcher m = p.matcher(line);
                if (m.find()) { // If our regex matched this line
                    // Parse it
                    Integer x1 = Integer.parseInt(m.group(1));
                    Integer y1 = Integer.parseInt(m.group(2));
                    Integer x2 = Integer.parseInt(m.group(3));
                    Integer y2 = Integer.parseInt(m.group(4));
                    inputList.add(new VentDefinition(x1, y1, x2, y2));
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
