package org.jgoeres.adventofcode2021.Day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02Service {
    public boolean DEBUG = false;

    private List<Submarine2DStep> inputList = new ArrayList<>();

    public Day02Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day02Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 2A ===");

        /** Calculate the horizontal position and depth you would have after following
         *  the planned course. What do you get if you multiply your final horizontal
         *  position by your final depth?
         **/
        final Submarine2DLocation subLocation = new Submarine2DLocation(); // starts at 0,0

        // Process the steps
        inputList.stream()
                .forEach(step -> subLocation.move(step.getDirection(), step.getDistance()));

        System.out.println(MessageFormat.format("Final location:\tforward = {0}\tdepth = {1}",
                subLocation.getLocation().getX(), subLocation.getLocation().getY()));
        final long result = subLocation.getLocation().getX() * subLocation.getLocation().getY();
        System.out.println("Day 2A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 2B ===");

        /** In addition to horizontal position and depth, you'll also need to track a third value,
         * aim, which also starts at 0.
         *
         * The commands also mean something entirely different than you first thought:
         * - down X increases your aim by X units.
         * - up X decreases your aim by X units.
         *
         * forward X does two things:
         * - It increases your horizontal position by X units.
         * - It increases your depth by your aim multiplied by X.
         *
         * Using this new interpretation of the commands, calculate the horizontal position and
         * depth you would have after following the planned course.
         *
         * What do you get if you multiply
         * your final horizontal position by your final depth?
         **/
        final Submarine2DWithAim subLocation = new Submarine2DWithAim(); // starts at 0,0

        // Process the steps
        inputList.stream()
                .forEach(step -> subLocation.move(step.getDirection(), step.getDistance()));

        System.out.println(MessageFormat.format("Final location:\tforward = {0}\tdepth = {1}",
                subLocation.getLocation().getX(), subLocation.getLocation().getY()));
        final long result = subLocation.getLocation().getX() * subLocation.getLocation().getY();
        System.out.println("Day 2B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            /** Replace this regex **/
            Pattern p = Pattern.compile("(forward|down|up)\\s(\\d+)");
            while ((line = br.readLine()) != null) {
                // process the line.
                Matcher m = p.matcher(line);
                if (m.find()) { // If our regex matched this line
                    // Parse it
                    String direction = m.group(1);
                    Integer distance = Integer.valueOf(m.group(2));
                    inputList.add(new Submarine2DStep(direction, distance));
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
