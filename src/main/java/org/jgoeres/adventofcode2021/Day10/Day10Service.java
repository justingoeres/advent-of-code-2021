package org.jgoeres.adventofcode2021.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day10Service {
    public boolean DEBUG = false;

    private ArrayList<String> inputList = new ArrayList<>();

    public Day10Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day10Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 10A ===");

        long result = 0;
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

        Stack<Opener> navigationStack = new Stack<>();
        List<Closer> mismatchedClosers = new ArrayList<>();
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
                        break;
                    }
                }
            }
        }
        // Now add up the scores
        result = mismatchedClosers.stream()
                .map(closer -> closer.getScore()).reduce(Integer::sum).get();
        System.out.println("Day 10A: Answer = " + result);
        return result;
    }

    private Boolean isMatched(Character character, Stack<Opener> stack) {
        // We found a closer! Pop the stack and see if it matches
        Optional<Closer> closer = Closer.getCloserByChar(character);
        Opener fromStack = stack.pop();
//        System.out.println("Pair found:\t" + fromStack.getCharacter() + " " + character);
        // True if this closer matches the top of the stack
        return closer.get().matches(fromStack);
    }

    public long doPartB() {
        System.out.println("=== DAY 10B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 10B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
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
