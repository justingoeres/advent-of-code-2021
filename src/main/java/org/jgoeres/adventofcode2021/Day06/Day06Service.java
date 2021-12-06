package org.jgoeres.adventofcode2021.Day06;

import org.jgoeres.adventofcode.common.Utils.Pair;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day06Service {
    public boolean DEBUG = false;

    private ArrayList<Integer> inputList = new ArrayList<>();
    private static final Integer TOTAL_DAYS = 80;
    private static final Integer TOTAL_DAYS_PART_B = 256;
    private static final Integer GENERATION_LENGTH = 6;    // days to reproduce

    private FishTimerCounts fishTimersToCounts = new FishTimerCounts(GENERATION_LENGTH);
    private FishTimerCounts nextFishTimersToCounts = new FishTimerCounts(GENERATION_LENGTH);
    private Pair<FishTimerCounts>
            timerMaps = new Pair(fishTimersToCounts, nextFishTimersToCounts);

    public Day06Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day06Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 6A ===");

        /** Find a way to simulate lanternfish.
         * How many lanternfish would there be after 80 days? **/
        for (int day = 0; day < TOTAL_DAYS; day++) {
            doTimerTick();
        }
        // when we're done, count 'em up
        long result = nextFishTimersToCounts.getMap().values().stream()
                .collect(Collectors.summingLong(Long::longValue));
        System.out.println("Day 6A: Answer = " + result);
        return result;
    }

    private void doTimerTick() {
//        Initial state: 3,4,3,1,2
//        After  1 day:  2,3,2,0,1
//        After  2 days: 1,2,1,6,0,8
//        After  3 days: 0,1,0,5,6,7,8
//        After  4 days: 6,0,6,4,5,6,7,8,8
        final Integer BABY_FISH_TIMER = 8;
        // each day
        // get the maps we need for "current" & "next"
        fishTimersToCounts = timerMaps.getFirst();
        nextFishTimersToCounts = timerMaps.getSecond();

        // clear the nextMap
        nextFishTimersToCounts.getMap().clear();
        // examine the fish at each tick value
        for (Map.Entry<Integer, Long> entry : fishTimersToCounts.getMap().entrySet()) {
            final Integer timer = entry.getKey();
            final Long count = entry.getValue();
            if (timer == 0) {
                // if timer is 0
                // * add that many 8s to the nextMap
                nextFishTimersToCounts.putTimerCount(BABY_FISH_TIMER, count);
                // * add same number of 6s to nextMap
                nextFishTimersToCounts.putTimerCount(GENERATION_LENGTH, count);
            } else {
                // otherwise
                // * add same number of timer-1s to nextMap
                nextFishTimersToCounts.putTimerCount(timer - 1, count);
            }
        }
        // place the result in the NEW timersToCounts map as we go
        // swap the Maps.
        timerMaps.swap();
    }

    public long doPartB() {
        System.out.println("=== DAY 6B ===");

        /** now do 256 days! **/
        for (int day = 0; day < TOTAL_DAYS_PART_B; day++) {
            doTimerTick();
        }
        // when we're done, count 'em up
        long result = nextFishTimersToCounts.getMap().values().stream()
                .collect(Collectors.summingLong(Long::longValue));

        System.out.println("Day 6B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            // e.g. 3,4,3,1,2
            final Pattern p = Pattern.compile("(\\d+)");
            line = br.readLine();   // only one line in this input
            // process the line.
            Matcher m = p.matcher(line);
            while (m.find()) { // while there are more Integers to match
                // Parse each one
                Integer timerStart = Integer.parseInt(m.group(1));
                if (fishTimersToCounts.getMap().containsKey(timerStart)) {
                    // if this timer count already exists, increment it
                    fishTimersToCounts.getMap().compute(timerStart, (key, value) ->
                            value + 1);
                } else {
                    // if the time count doesn't exist, create it
                    fishTimersToCounts.getMap().put(timerStart, 1L);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
