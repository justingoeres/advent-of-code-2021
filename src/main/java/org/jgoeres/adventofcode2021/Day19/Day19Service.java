package org.jgoeres.adventofcode2021.Day19;

import static org.jgoeres.adventofcode.common.XYZPoint.Axis.*;
import static org.jgoeres.adventofcode.common.XYZPoint.ORIGIN_XYZ;
import org.jgoeres.adventofcode.common.RotationStep;
import org.jgoeres.adventofcode.common.XYZPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19Service {
    public boolean DEBUG = false;

    private final Map<Integer, Scanner> scanners = new HashMap<>();
    private final Set<XYZPoint> knownUniverse = new HashSet<>();


    public Day19Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day19Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 19A ===");

        /**
         * The scanners and beacons map a single contiguous 3d region.
         * This region can be reconstructed by finding pairs of scanners
         * that have overlapping detection regions such that there are
         * at least 12 beacons that both scanners detect within the
         * overlap. By establishing 12 common beacons, you can precisely
         * determine where the scanners are relative to each other,
         * allowing you to reconstruct the beacon map one scanner at a
         * time.
         *
         * Assemble the full map of beacons. How many beacons are there?
         **/

        // Add all of scanner 0 to the known universe
        scanners.get(0).getBeacons().stream().forEach(xyz -> knownUniverse.add(xyz));

        // If we take Scanner 0 as our reference for position & orientation
        // Then Take point 0 of scanner 0.
        // Calculate all the RELATIVE beacon coordinates TO THAT POINT.
        // That gives us a search space anchored on a beacon that MAY exist
        // on other scanners

        // referenceBeacons are the set of beacons for the CURRENT scanner,
        // translated so that ONE of the beacons is at 0,0,0
        final Scanner referenceScanner = scanners.get(0);
        // Remove scanner 0 because we don't need to reprocess it
        scanners.remove(0);

//        final XYZPoint referenceBeacon = referenceScanner.getBeacons().get(0);
        // From there, take each scanner (our 'scannerEntry').
        // Take each beacon on that scanner (our 'candidateReference').

        while (!scanners.isEmpty()) {
            System.out.println("=== NEW SCAN === (" + scanners.size() +
                    " scanners unmapped; universe size:\t " + knownUniverse.size() + ")");
            Set<XYZPoint> beaconsToAdd = new HashSet<>();
            for (XYZPoint referenceBeacon : knownUniverse) {
//                System.out.println("Referencing to knownUniverse point (" + referenceBeacon + ")");
//                final List<XYZPoint> referenceBeacons =
//                        referenceScanner.getBeaconsToReference(referenceBeacon);
                List<XYZPoint> referenceBeacons =
                        XYZPoint.getXYZToReference(knownUniverse, referenceBeacon);
                Set<Integer> scannersToRemove = new HashSet<>();
                for (final Map.Entry<Integer, Scanner> scannerEntry : scanners.entrySet()) {
                    //                scanners.remove(scannerEntry.getKey());
//                    System.out.println("Checking scanner " + scannerEntry.getKey());
                    //            Scanner candidateScanner = scanners.get(1);
                    Scanner candidateScanner = scannerEntry.getValue();
                    for (XYZPoint candidateReference : candidateScanner.getBeacons()) {
                        //                    System.out.println(
                        //                            "Checking candidate reference point:\t" +
                        //                                    candidateReference.toString());
                        // Calculate the RELATIVE beacon coordinates TO THAT POINT.
                        final List<XYZPoint> candidateBeacons =
                                candidateScanner.getBeaconsToReference(candidateReference);
                        // See if they match the coordinates of enough reference beacons
                        final Boolean foundMatchingRotation =
                                checkAllRotations(referenceBeacons, candidateBeacons);
                        if (foundMatchingRotation) {
//                            System.out.println("Found a match!");
                            // If we found a matching rotation, next we need to
                            // 1. translate all the beacons into "universe" coordinates
                            //      by moving them relative to the referenceBeacon
                            // 2. Add them to the known universe
                            //                candidateBeacons.stream().forEach(xyz -> xyz.translate(referenceBeacon));
                            //                candidateBeacons.stream().forEach(xyz -> knownUniverse.add(xyz));
                            candidateBeacons.stream().forEach(xyz -> {
                                xyz.translate(referenceBeacon);
//                                knownUniverse.add(xyz);
                                beaconsToAdd.add(xyz);
                                // Finally, remove this scanner from the remaining scanners to check
                            });
                            scannersToRemove.add(scannerEntry.getKey());
                            break;
                        }
                    }
                }
                scannersToRemove.stream().forEach(c -> scanners.remove(c));
            }
            knownUniverse.addAll(beaconsToAdd);
        }
        // So try again, but with the scanner-1-relative set ROTATED.

        //
        // If we get through all 24 rotations without a match
        // start over with the NEXT point in scanner 1 (point 1).
        //
        // If we get all the way through all the scanner 1 points without a match,
        // Repeat all of the above with scanner 2.

        // If we DO find a match, then convert all that scanner's beacon
        // coordinates to relative-to-scanner-0 and
        // add them to our "known universe" Set(?)
        // By doing this, we build up a "map of the universe" gradually
        // And can keep matching scanners to it until we're done

        long result = knownUniverse.size();
        System.out.println("Day 19A: Answer = " + result);
        return result;
    }

    private Boolean checkAllRotations(final List<XYZPoint> referenceBeacons,
                                      final List<XYZPoint> candidateBeacons) {
        for (final RotationStep rotation : allRotations) {
            // Rotate the candidateBeacons into the orientation we're testing
//            System.out.println(
//                    "Rotating around " + rotation.getAxis() + " " + rotation.getTimes() +
//                            " time(s)");
            for (final XYZPoint beacon : candidateBeacons) {
                beacon.rotate(rotation.getAxis(), rotation.getTimes());
            }
            // Now check all the candidate (rotated) candidate beacons against our reference
            // If any match a coordinate in the relative-Scanner-0 list, increment
            // a counter.
            Integer matchCounter = 0;
            for (final XYZPoint beacon : candidateBeacons) {
                if (referenceBeacons.contains(beacon)) {
                    matchCounter++;
//                    if (!beacon.equals(ORIGIN_XYZ)) {   // Of course the origin matches
//                        System.out.println(
//                                "Matched beacon at (" + beacon + ")\tMatch count:\t" +
//                                        matchCounter);
//                    }
                }
                // Can cut this off early if we need to
                if (matchCounter >= 12) {
//                    System.out.println("We found at least 12 matches!!!");
                    return true; // we found a match!
                }
            }
        }
        // If the counter doesn't reach 12, we do not have a match.
        return false;
    }

    public long doPartB() {
        System.out.println("=== DAY 19B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 19B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        scanners.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            final Pattern p1 = Pattern.compile("--- scanner (\\d+)");
            final Pattern p2 = Pattern.compile("(-?\\d{1,3}),(-?\\d{1,3}),(-?\\d{1,3})");
            Scanner scanner = null;
            while ((line = br.readLine()) != null) {
                // process the line.
                final Matcher m1 = p1.matcher(line);
                final Matcher m2 = p2.matcher(line);
                if (m1.find()) { // If this is a new scanner
                    // Create a scanner
                    scanner = new Scanner();
                    scanners.put(Integer.parseInt(m1.group(1)), scanner);
                } else if (m2.find()) { // If this is a beacon
                    // Create a beacon and add it to the scanner
                    final Integer x = Integer.parseInt(m2.group(1));
                    final Integer y = Integer.parseInt(m2.group(2));
                    final Integer z = Integer.parseInt(m2.group(3));
                    final XYZPoint beacon = new XYZPoint(x, y, z);
                    scanner.add(beacon);
                }
            }
            // Stick scanner 0 at 0,0,0
            scanners.get(0).setPosition(new XYZPoint(0, 0, 0));
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e);
        }
    }

    private static final List<RotationStep> allRotations = List.of(
            new RotationStep(NONE), // First rotation is "just do the original state"
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(Z_AXIS, 2),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(Z_AXIS, 1),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(Z_AXIS, 2),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(Y_AXIS, 1),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(Z_AXIS, 2),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS),
            new RotationStep(X_AXIS)
    );
}
