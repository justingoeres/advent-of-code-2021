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

    final private Set<XYPoint> xyPoints = new HashSet<>();
    final private List<Fold> folds = new ArrayList<>();

    public Day13Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day13Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 13A ===");

        /**
         * How many dots are visible after completing just the first fold instruction on your transparent paper?
         **/
        final Fold firstFold = folds.get(0);
        doFold(xyPoints, firstFold);
        // Answer is just the number of points left
        final long result = xyPoints.size();

        System.out.println("Day 13A: Answer = " + result);
        return result;
    }

    public void doPartB() {
        System.out.println("=== DAY 13B ===");

        /**
         * Finish folding the transparent paper according to the instructions.
         * The manual says the code is always eight capital letters.
         * What code do you use to activate the infrared thermal imaging camera system?
         **/

        // We don't need to worry about whether we've already done the first fold,
        // since doing it again will just be a no-op
        folds.stream().forEach(fold -> doFold(xyPoints, fold));

        // Before we print the answer, we need to find the size of the remaining area.
        // The size is just the MINIMUM fold coordinate for each direction.
        final Integer xMax = folds.stream().filter(p -> p.getDirection().equals(HORIZONTAL))
                .map(fold -> fold.getCoord()).min(Integer::compareTo).get();

        final Integer yMax = folds.stream().filter(p -> p.getDirection().equals(VERTICAL))
                .map(fold -> fold.getCoord()).min(Integer::compareTo).get();

        System.out.println("Day 13B: Answer =");
        printPaper(xyPoints, xMax, yMax);
    }

    private void doFold(final Set<XYPoint> xyPoints, final Fold fold) {
        // Get all the x or y points beyond the fold line, based on the direction of the fold
        final Set<XYPoint> pointsToFold = xyPoints.stream()
                .filter(p -> ((fold.getDirection().equals(HORIZONTAL)) ? p.getX() : p.getY()) >
                        fold.getCoord())
                .collect(Collectors.toSet());

        for (final XYPoint point : pointsToFold) {
            // For each point, "flip" it in the x direction around the fold point
            // Calculate the new X
            final Integer newXorY = fold.getCoord() -
                    (((fold.getDirection().equals(HORIZONTAL)) ? point.getX() : point.getY()) -
                            fold.getCoord());
            // Remove this point from XYPoints, and reinsert it at the new coords
            xyPoints.remove(point);
            if (fold.getDirection().equals(HORIZONTAL)) {
                point.setX(newXorY);
            } else {
                point.setY(newXorY);
            }
            xyPoints.add(point);
        }
    }

    private void printPaper(Set<XYPoint> xyPoints, Integer xMax, Integer yMax) {
        for (int y = 0; y < yMax; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < xMax; x++) {
                sb.append(xyPoints.contains(new XYPoint(x, y)) ? "#" : ".");
            }
            System.out.println(sb);
        }
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        xyPoints.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            // Read the xy points
            final Pattern p1 = Pattern.compile("(\\d+),(\\d+)");
            while (!(line = br.readLine())
                    .equals("")) {  // everything up to the first blank line is coordinates
                // process the line.
                final Matcher m = p1.matcher(line);
                if (m.find()) { // If our regex matched this line
                    // Parse it
                    final String field1 = m.group(1);
                    final String field2 = m.group(2);
                    XYPoint xy = new XYPoint(Integer.parseInt(field1), Integer.parseInt(field2));
                    xyPoints.add(xy);
                }
            }
            // Now read the folding instructions
            final Pattern p2 = Pattern.compile("fold along ([xy])=(\\d+)");
            while ((line = br.readLine()) != null) {
                // process the line.
                final Matcher m = p2.matcher(line);
                if (m.find()) { // If our regex matched this line
                    // Parse it
                    final String field1 = m.group(1);
                    final String field2 = m.group(2);
                    // 'x' folds are vertical lines, so the fold direction is horizontal
                    final FoldDirection foldDirection =
                            (field1.equals("x")) ? HORIZONTAL : VERTICAL;
                    final Fold fold = new Fold(foldDirection, Integer.parseInt(field2));
                    folds.add(fold);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
