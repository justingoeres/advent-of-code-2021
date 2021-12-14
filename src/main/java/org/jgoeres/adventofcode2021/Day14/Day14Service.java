package org.jgoeres.adventofcode2021.Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14Service {
    public boolean DEBUG = false;

    private final LinkedList<String> inputList = new LinkedList<>();
    private final Map<String, String> insertions = new HashMap<>();

    public Day14Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day14Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 14A ===");
        final Integer PARTA_STEPS = 10;
        // First make a copy of the template, since we'll probably reuse it for Part B
        final LinkedList<String> chain = (LinkedList) inputList.clone();

        for (int step = 0; step < PARTA_STEPS; step++) {
            final ListIterator<String> chainIter = chain.listIterator();
            doInsertions(chainIter);
//            printStepResult(chain, step);
        }

        /**
         * What do you get if you take the quantity of the most common element
         * and subtract the quantity of the least common element?
         **/

        Map<Character, Long> frequencies =
                chain.stream().collect(Collectors.joining(""))
                        .chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Long maxFrequency = frequencies.values().stream().mapToLong(v -> v).max().getAsLong();
        Long minFrequency = frequencies.values().stream().mapToLong(v -> v).min().getAsLong();
        long result = maxFrequency - minFrequency;

        System.out.println("Day 14A: Answer = " + result);
        return result;
    }

    private void doInsertions(final ListIterator<String> chainIter) {
        while (chainIter.hasNext()) {
            // Get the next TWO elements, since that's our pair
            try {
                final String pair1 = chainIter.next();
                final String pair2 = chainIter.next();
                // Then immediately move back one since that's where we'll insert
                chainIter.previous();
                final String pair = pair1 + pair2;
                // If there's an insertion for this pair
                if (insertions.containsKey(pair)) {
                    // insert between the current pair
                    chainIter.add(insertions.get(pair));
                }
            } catch (NoSuchElementException e) {
                // This is the normal outcome of trying to get pair 2
                // when we're at the end of the list, so just continue
            }
        }
    }

    private void printStepResult(final List<String> chain, final Integer step) {
        final StringBuilder sb = new StringBuilder();
        chain.stream().forEach(c -> sb.append(c));
        System.out.println(MessageFormat.format("After step {0}: {1}", step + 1, sb));
    }

    public long doPartB() {
        System.out.println("=== DAY 14B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 14B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        inputList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextInt = 0;
            // Read the first line (the starting chain)
            line = br.readLine();
            // Make it into a LinkedList
            Arrays.stream(line.split("")).forEach(c -> inputList.add(c));
            br.readLine();  // skip the blank line
            /** Replace this regex **/
            Pattern p = Pattern.compile("(\\w{2}) -> (\\w)");
            while ((line = br.readLine()) != null) {
                // process the line.
                Matcher m = p.matcher(line);
                if (m.find()) { // If our regex matched this line
                    // Parse it
                    String pair = m.group(1);
                    String insertion = m.group(2);
                    insertions.put(pair, insertion);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
