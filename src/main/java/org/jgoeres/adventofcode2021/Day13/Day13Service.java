package org.jgoeres.adventofcode2021.Day13;

import static org.jgoeres.adventofcode2021.Day13.Fold.FoldDirection.*;
import static org.jgoeres.adventofcode2021.Day13.Fold.FoldDirection.VERTICAL;
import org.jgoeres.adventofcode.common.XYPoint;
import org.jgoeres.adventofcode2021.Day13.Fold.FoldDirection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day13Service {
    public boolean DEBUG = false;

    private Set<XYPoint> xyPoints = new HashSet<>();

    private List<Fold> folds = new ArrayList<>();

    public Day13Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day13Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 13A ===");

        long result = 0;
        /**
         * How many dots are visible after completing just the first fold instruction on your transparent paper?
         **/

        // To fold, we take the coordinate of the fold and move OUT (in the + direction) from there.
        // For each point we find, we "flip" it to be -n steps from the fold line instead of +n.

        Fold firstFold = folds.get(0);
        Set<XYPoint> pointsToFold;
        if (firstFold.getDirection() == HORIZONTAL) {
            pointsToFold = xyPoints.stream().filter(p -> p.getX() > firstFold.getCoord())
                    .collect(Collectors.toSet());
            for (XYPoint point : pointsToFold) {
                // For each point, "flip" it in the x direction around the fold point
                // Calculate the new X
                Integer newX = firstFold.getCoord() - (point.getX() - firstFold.getCoord());
                // Remove this point from XYPoints, and reinsert it at the new coords
                xyPoints.remove(point);
                point.setX(newX);
                xyPoints.add(point);
            }
        } else {
            pointsToFold = xyPoints.stream().filter(p -> p.getY() > firstFold.getCoord())
                    .collect(Collectors.toSet());
        }

        // Answer is just the number of points left
        result = xyPoints.size();
        System.out.println("Day 13A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 13B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 13B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        xyPoints.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            /** Replace this regex **/
            final Pattern p1 = Pattern.compile("(\\d+),(\\d+)");
            final Pattern p2 = Pattern.compile("fold along ([xy])=(\\d+)");
            // Read the xy points
            while (!(line = br.readLine())
                    .equals("")) {  // everything up to the first blank line is coordinates
                // process the line.
                final Matcher m = p1.matcher(line);
                if (m.find()) { // If our regex matched this line
                    // Parse it
                    String field1 = m.group(1);
                    String field2 = m.group(2);
                    XYPoint xy = new XYPoint(Integer.parseInt(field1), Integer.parseInt(field2));
                    xyPoints.add(xy);
                }
            }
            // Now read the folding instructions
            while ((line = br.readLine()) !=
                    null) {  // everything after the first blank line is folding steps
                // process the line.
                final Matcher m = p2.matcher(line);
                if (m.find()) { // If our regex matched this line
                    // Parse it
                    String field1 = m.group(1);
                    String field2 = m.group(2);
                    // 'x' folds are vertical lines, so the fold direction is horizontal
                    FoldDirection foldDirection = (field1.equals("x")) ? HORIZONTAL : VERTICAL;
                    Fold fold = new Fold(foldDirection, Integer.parseInt(field2));
                    folds.add(fold);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
