package org.jgoeres.adventofcode2021.Day19;

import org.jgoeres.adventofcode.common.XYZPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19Service {
    public boolean DEBUG = false;

    private final Map<Integer, Scanner> scanners = new HashMap<>();

    public Day19Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day19Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 19A ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 19A: Answer = " + result);
        return result;
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
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
