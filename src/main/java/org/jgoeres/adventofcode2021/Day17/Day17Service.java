package org.jgoeres.adventofcode2021.Day17;

import org.jgoeres.adventofcode.common.Area;
import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public long doPartB() {
        System.out.println("=== DAY 17B ===");

        /**
         * How many distinct initial velocity values cause the probe
         * to be within the target area after any step?
         **/

        // OK, so we know the most negative initial y-velocity that can possibly
        // hit the target area is -109 (the lower boundary of the target)
        // and the most positive initial y-velocity is +108
        //
        // So in any case we only need to search in y from -109 to +108

        // Find all y-velocities that EVER enter the target area
        final Integer yV0min = -109;
        final Integer yV0may = 108;

        // Map times hitting the target initial y velocities
        Map<Integer, List<Integer>> yTargetHits = new HashMap<>();
        Map<Integer, List<Integer>> yFinalHits = new HashMap<>();
        // for each velocity
        for (Integer yV0 = yV0min; yV0 <= yV0may; yV0++) {
            Integer y = 0;  // start from y=0 every time
            Integer yV = yV0;   // initial velocity
//            System.out.println("Initial y velocity:\t" + yV0);
            // simulate time
            Integer t = 0;
            while (y >= target.getLowerRight().getY()) {
                // the probe's y position increases by its y velocity.
                y += yV;
                t += 1;
//                System.out.println(
//                        "t = " + t + "\ty = " + y + "\tcontains?\t" + target.yContains(y));
                // If the probe is in the target area RIGHT NOW,
                // map the current time to the yV0 that got us here.
                if (target.yContains(y)) {
//                    System.out.println("HIT!");
                    List<Integer> tHits;
                    if (yTargetHits.containsKey(t)) {
                        tHits = yTargetHits.get(t);
                    } else {
                        tHits = new ArrayList<>();
                        yTargetHits.put(t, tHits);
                    }
                    tHits.add(yV0);
                }
                // the probe's y velocity changes by -1
                yV -= 1;
            }
        }

        // The X case is kind of similar. The area is +179 to +201
        // So the maximum initial x-velocity that can EVER hit the target is +201
        // and the minimum is one that just barely gets to the left edge
        // fits the equation Xmax = XVInit*(xVInit+1) / 2;
        //
        // Some math tells us eventually that xVinit of 19 will hit x of 190
        // (xMax for an xVinit of 18 will only get to 171)
        //
        // So our x search area is from +19 to +201

        // Find all x-velocities that EVER enter the target area
        final Integer xV0min = 19;
        final Integer xV0max = 201;

        // Map times hitting the target initial x velocities
        Map<Integer, List<Integer>> xTargetHits = new HashMap<>();
        Map<Integer, List<Integer>> xFinalHits = new HashMap<>();
        // for each velocity
        for (Integer xV0 = xV0min; xV0 <= xV0max; xV0++) {
            Integer x = 0;  // start from x=0 every time
            Integer xV = xV0;   // initial velocity
//            System.out.println("Initial x velocity:\t" + xV0);
            // simulate time
            Integer t = 0;
            while (x <= target.getLowerRight().getX() && xV > 0) {
                // the probe's x position increases by its x velocity.
                x += xV;
                t += 1;
//                System.out.println(
//                        "t = " + t + "\tx = " + x + "\tcontains?\t" + target.xContains(x));
                // If the probe is in the target area RIGHT NOW,
                // map the current time to the xV0 that got us here.
                if (target.xContains(x)) {
//                    System.out.println("HIT!");
                    List<Integer> tHits;
                    if (xTargetHits.containsKey(t)) {
                        tHits = xTargetHits.get(t);
                    } else {
                        tHits = new ArrayList<>();
                        xTargetHits.put(t, tHits);
                    }
                    tHits.add(xV0);
                }
                // the probe's x velocity changes by 1 toward the value 0
                xV -= 1;
            }
            // The probe has stopped. Will it be in the target forever?
            if (target.xContains(x)) {
//                System.out.println("HIT AND STAY!");
                List<Integer> tHits;
                if (xFinalHits.containsKey(t + 1)) {
                    tHits = xTargetHits.get(t + 1);
                } else {
                    tHits = new ArrayList<>();
                    xFinalHits.put(t + 1, tHits);
                }
                tHits.add(xV0);
            }
        }

        // Now we have all the yV0s & xV0s that will possibly pass through the target area,
        // along with the times during which all of them will BE in the target area.
        // So the total number of combinations is all the combinations at EACH time t
        Set<XYPoint> hitCombos = new HashSet<>();

        // There are fewer x-times that hit, so iterate those
        for (Map.Entry<Integer, List<Integer>> xHit : xTargetHits.entrySet()) {
            Integer t = xHit.getKey();  // Time of this xHit
            List<Integer> vX0s = xHit.getValue();   // All the vX0s that hit at this time

            List<Integer> vY0s = yTargetHits.get(t); // All the vY0s that hit at this time

            for (Integer vX0 : vX0s) {
                for (Integer vY0 : vY0s) {
                    hitCombos.add(new XYPoint(vX0, vY0));
                }
            }
        }

        // Then we also have to add in all the y-velocities that will pass through
        // the target after xV0 = 19 has stopped and stayed there
        for (Map.Entry<Integer, List<Integer>> xHit : xFinalHits.entrySet()) {
            Integer t = xHit.getKey();  // Time of this xHit
            List<Integer> vX0s = xHit.getValue();   // All the vX0s that hit at this time
            for (Integer vX0 : vX0s) {
                for (Map.Entry<Integer, List<Integer>> vY0s : yTargetHits.entrySet()) {
                    if (vY0s.getKey() >= t) {
                        // If these vY0s will pass through the target AT OR AFTER the x motion stops
                        for (Integer vY0 : vY0s.getValue()) {
                            hitCombos.add(new XYPoint(vX0, vY0));
                        }
                    }
                }
            }
        }
        final Integer result = hitCombos.size();
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
