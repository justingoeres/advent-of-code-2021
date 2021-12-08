package org.jgoeres.adventofcode2021.Day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08Service {
    public boolean DEBUG = false;

    private static final List<Display> inputList = new ArrayList<>();

    private static final Set<Character> ALL_LETTERS = Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g');
    private static final Set<Character> LETTERS_NOT_IN_ONE = Set.of('a', 'b', 'd', 'e', 'g');
    private static final Set<Character> LETTERS_NOT_IN_FOUR = Set.of('a', 'e', 'g');

    private static final Set<Character> ONE = Set.of('c', 'f');
    private static final Set<Character> TWO = Set.of('a', 'c', 'd', 'e', 'g');
    private static final Set<Character> THREE = Set.of('a', 'c', 'd', 'f', 'g');
    private static final Set<Character> FOUR = Set.of('b', 'c', 'd', 'f');
    private static final Set<Character> FIVE = Set.of('a', 'b', 'd', 'f', 'g');
    private static final Set<Character> SIX = Set.of('a', 'b', 'd', 'e', 'f', 'g');
    private static final Set<Character> SEVEN = Set.of('a', 'c', 'f');
    private static final Set<Character> EIGHT = Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g');
    private static final Set<Character> NINE = Set.of('a', 'b', 'c', 'd', 'f', 'g');
    private static final Set<Character> ZERO = Set.of('a', 'b', 'c', 'e', 'f', 'g');

    private static final List<Set<Character>> digitReferences =
            List.of(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE);

    public Day08Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day08Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 8A ===");

        long result = 0;
        /** Because the digits 1, 4, 7, and 8 each use a unique number of segments,
         * you should be able to tell which combinations of signals correspond to
         * those digits. Counting only digits in the output values
         * (the part after | on each line),
         * in the output values, how many times do digits 1, 4, 7, or 8 appear?
         **/

        // "1" is 2 segments
        // "4" is 4 segments
        // "7" is 3 segments
        // "8" is 7 segments

        // Count the lengths of the items in the "digits" list
        int count = 0;
        for (Display display : inputList) {
            for (String digit : display.getDigits()) {
                int length = digit.length();
                if (length == 2 || length == 4 || length == 3 || length == 7) {
                    count++;
                }
            }
        }
        System.out.println("Day 8A: Answer = " + count);
        return count;
    }

    public long doPartB() {
        System.out.println("=== DAY 8B ===");

        long result = 0;
        /** For each entry, determine all of the wire/segment connections and decode
         * the four-digit output values. What do you get if you add up all of the output values?
         **/

        for (Display inputLine : inputList) {
            List<String> patterns = inputLine.getPatterns();
            List<String> digits = inputLine.getDigits();

            Map<Character, Integer> counts = initCountsMap();

            // Count up how many times each letter appears in this pattern set and put the
            // counts in 'counts'

            patterns.stream().forEach(
                    c -> c.chars()
                            .forEach(d -> counts.compute((char) d, (key, value) -> value + 1)));

            // Now use that information to map out possible pattern -> segment characters
            // If a letter occurs
            //                      4 times it's an E
            //                      6 times it's a  B
            //                      7 times it's a  D or G
            //                      8 times it's an A or C
            //                      9 times it's a  F

            Map<Character, Set<Character>> patternToSegments = new HashMap<>();
            for (Entry<Character, Integer> patternCharacter : counts.entrySet()) {
                Set<Character> possibleCharacters = new HashSet<>();
                switch (patternCharacter.getValue()) {
                    case 4: // It's an e
                        possibleCharacters.add('e');
                        break;
                    case 6: // It's a b
                        possibleCharacters.add('b');
                        break;
                    case 7: // It's a d or g
                        possibleCharacters.add('d');
                        possibleCharacters.add('g');
                        break;
                    case 8: // It's an a or c
                        possibleCharacters.add('a');
                        possibleCharacters.add('c');
                        break;
                    case 9: // It's an f
                        possibleCharacters.add('f');
                        break;
                }
                patternToSegments.put(patternCharacter.getKey(), possibleCharacters);
            }

            // All we have to do now is differentiate between d/g and a/c

            for (String pattern : patterns) {
                // For a vs. c, 'c' is used in '1' which is the two-segment pattern (along with 'f' which is known!)
                // Therefore the segment in the '1' that is NOT known must be 'c'!
                if (pattern.length() == 2) {
                    // Check both characters in this pattern against our patternToSegments map
                    // For each one, REMOVE all letters except 'c' and 'f'. This should leave ONE possibleCharacter
                    // Then remove THAT remaining character from everything else
                    for (char c : pattern.toCharArray()) {
                        patternToSegments.get(c).removeAll(LETTERS_NOT_IN_ONE);
                        if (patternToSegments.get(c).size() == 1) {
                            // If there is now only one possible character for this to map, to
                            // REMOVE that character from everything else
                            patternToSegments.entrySet().stream()
                                    .filter(p -> p.getValue().size() != 1)
                                    .forEach(d -> d.getValue().removeAll(patternToSegments.get(c)));
                        }

                    }
                }
                // For d vs. g, 'g' is used in '4' which is the four-segment pattern
                // (along with 'b', 'c', and 'd' which are known!)
                // Therefore the segment in the '4' that is NOT known must be 'g'!
                if (pattern.length() == 4) {
                    // Check the characters in this pattern against our patternToSegments map
                    // For each one, REMOVE all letters except 'b', 'c', 'd', and 'g'.
                    // This should leave ONE possibleCharacter
                    // Then remove THAT remaining character from everything else
                    for (char c : pattern.toCharArray()) {
                        patternToSegments.get(c).removeAll(LETTERS_NOT_IN_FOUR);
                        if (patternToSegments.get(c).size() == 1) {
                            // If there is now only one possible character for this to map, to
                            // REMOVE that character from everything else
                            patternToSegments.entrySet().stream()
                                    .filter(p -> p.getValue().size() != 1)
                                    .forEach(d -> d.getValue().removeAll(patternToSegments.get(c)));
                        }
                    }
                }
            }
            // And believe it or not, now our mapping is complete!
            // Now we just have to figure out what the digits are.
            Integer digitsValue = 0;
            for (String digit : digits) {
                // Convert this digit to unscrambled segments
                Set<Character> digitSegments = new HashSet<>();
                for (Character c : digit.toCharArray()) {
                    // there's only one possible segment left in each pattern so we can get it with an iterator
                    digitSegments.add(patternToSegments.get(c).iterator().next());
                }
                // Now figure out what digit this is!
                for (int i = 0; i < digitReferences.size(); i++) {
                    Set<Character> reference = digitReferences.get(i);
                    if (digitSegments.size() == reference.size() &&
                            digitSegments.containsAll(reference)) {
                        // Then this digit matches
//                        System.out.println("Found digit " + i);
                        digitsValue = 10 * digitsValue + i;
                    }
                }
            }
            // Now we've processed all the digits, so add this to the total!
//            System.out.println("Found number:\t" + digitsValue);
            result += digitsValue;
        }

        System.out.println("Day 8B: Answer = " + result);
        return result;
    }

    private Map<Character, Integer> initCountsMap() {
        // Fill a new map with entries for each letter, that all say 0
        final Map<Character, Integer> countsMap = new HashMap<>();
        ALL_LETTERS.stream().forEach(c -> countsMap.put(c, 0));
        return countsMap;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            /** Each entry consists of ten unique signal patterns,
             * a | delimiter, and finally the four digit output value **/
            Pattern signalPattern = Pattern.compile("([a-g]+)\\s?");
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split("\\|");
                // process the first section (the patterns)
                Matcher m = signalPattern.matcher(splitLine[0]);
                List<String> patterns = new ArrayList<>();
                while (m.find()) { // If our regex matched this line
                    // Parse it
                    patterns.add(m.group(1));
                }
                m = signalPattern.matcher(splitLine[1]);
                List<String> digits = new ArrayList<>();
                while (m.find()) {
                    digits.add(m.group(1));
                }
                inputList.add(new Display(patterns, digits));
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
