package org.jgoeres.adventofcode2021.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Day10Service {
    public boolean DEBUG = false;

    private final ArrayList<String> inputList = new ArrayList<>();
    private final List<Long> autocompleteScores = new ArrayList<>();

    public Day10Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day10Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 10A ===");

        /**
         * Take the first illegal character on the line and look it up in the following table:
         *
         * ): 3 points.
         * ]: 57 points.
         * }: 1197 points.
         * >: 25137 points.
         *
         * Find the first illegal character in each corrupted line of the navigation subsystem.
         * What is the total syntax error score for those errors?
         * **/

        final Deque<Opener> navigationStack = new ArrayDeque<>();
        final List<Closer> mismatchedClosers = new ArrayList<>();
        for (String line : inputList) {
            // Process each line
//            System.out.println("Processing line:\t" + line);
            // Go character-by-character, pushing openers and popping closers
            for (Character character : line.toCharArray()) {
                // If this is an opener
                if (Opener.getOpenerByChar(character).isPresent()) {
                    // Put it on the stack
                    navigationStack.push(Opener.getOpenerByChar(character).get());
                } else if (Closer.getCloserByChar(character).isPresent()) {
                    // Or if this is a closer, see if it has a matching opener
                    // record it, and go to the next line
                    if (!isMatched(character, navigationStack)) {
//                        System.out.println("Mismatch detected!");
                        mismatchedClosers.add(Closer.getCloserByChar(character).get());
                        navigationStack.clear();    // clear the stack for the next line
                        break;
                    }
                }
            }
            // If we get here, we have an *incomplete* (but valid) line,
            // so (for part B) we can complete it by just unwinding the stack
            // let's figure out how to complete it
//            System.out.println("Line is incomplete...");
            final StringBuilder closingWith = new StringBuilder();
            Long autocompleteScore = navigationStack.stream().map(opener -> opener.getMatch())
                    .peek(closer -> closingWith.append(closer.getCharacter()))
                    .reduce(0L,
                            (subtotal, element) -> 5 * subtotal + element.getAutocompleteScore(),
                            Long::sum);
//            System.out.println("Closing with:\t" + closingWith + "\tScore:\t" + autocompleteScore + "\n");
            if (autocompleteScore > 0) {
                autocompleteScores.add(autocompleteScore);
            }
            navigationStack.clear(); // clear the stack for the next line

        }
        // Now add up the scores for Part A
        final Long result = mismatchedClosers.stream()
                .map(closer -> closer.getScore()).reduce(Long::sum).get();
        System.out.println("Day 10A: Answer = " + result);

        return result;
    }

    private Boolean isMatched(Character character, Deque<Opener> stack) {
        // We found a closer! Pop the stack and see if it matches
        Optional<Closer> closer = Closer.getCloserByChar(character);
        Opener fromStack = stack.pop();
//        System.out.println("Pair found:\t" + fromStack.getCharacter() + " " + character);
        // True if this closer matches the top of the stack
        return closer.get().matches(fromStack);
    }

    public long doPartB() {
        System.out.println("=== DAY 10B ===");

        /**
         * Find the completion string for each incomplete line,
         * score the completion strings, and sort the scores. What is the middle score?
         **/
        // And for Part B, get the median of the autocomplete scores
        Collections.sort(autocompleteScores);
        Long result = autocompleteScores
                .get(autocompleteScores.size() / 2); // gets the median of an odd-length array

        System.out.println("Day 10B: Answer = " + result);
        return result;
    }

    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // For this problem, just put the lines into a list without parsing yet.
                inputList.add(line);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
