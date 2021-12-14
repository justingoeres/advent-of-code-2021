package org.jgoeres.adventofcode2021.Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14Service {
    public boolean DEBUG = false;

    private String inputLine;
    private final LinkedList<String> chain = new LinkedList<>();
    private final Map<String, Character> insertions = new HashMap<>();

    Map<Character, Long> letterCounts;
    Map<String, Long> pairCounts;
    Map<String, Long> pairDeltas = new HashMap<>();

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

        final long result = doExpansionSteps(chain, PARTA_STEPS);
        System.out.println("Day 14A: Answer = " + result);
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 14B ===");
        final Integer PARTB_STEPS = 40;

        final long result = doExpansionSteps(chain, PARTB_STEPS);

        System.out.println("Day 14B: Answer = " + result);
        return result;
    }

    private long doExpansionSteps(final LinkedList<String> chain, final Integer steps) {
        // Init the letterCounts
        letterCounts = inputLine.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        pairCounts = new HashMap<>();
        ListIterator<String> pairIterator = chain.listIterator();
        while (pairIterator.hasNext()) {
            try {
                // Get the next TWO elements, since that's our pair
                final String pair1 = pairIterator.next();
                final String pair2 = pairIterator.next();
                final String pair = pair1 + pair2;
                // Increment the count for this pair
                pairCounts.put(pair, pairCounts.getOrDefault(pair, 0L) + 1);
                // Then immediately move back one since that's where our next pair starts
                pairIterator.previous();
            } catch (NoSuchElementException e) {
                // This is the normal outcome of trying to get pair 2
                // when we're at the end of the list, so just continue
            }
        }

        for (int step = 0; step < steps; step++) {
            final ListIterator<String> chainIter = chain.listIterator();
            doInsertions(chainIter);
            System.out.println("Step: " + (step + 1));
        }

        /**
         * What do you get if you take the quantity of the most common element
         * and subtract the quantity of the least common element?
         **/
        Long maxFrequency = letterCounts.values().stream().mapToLong(v -> v).max().getAsLong();
        Long minFrequency = letterCounts.values().stream().mapToLong(v -> v).min().getAsLong();
        long result = maxFrequency - minFrequency;
        return result;
    }

    private void doInsertions(final ListIterator<String> chainIter) {
        // Each insertion: (e.g. BN -> C)
        // * Adds a 'C' to the letterCounts
        // * Adds TWO new pairs to pairCounts: 'BC' & 'CN'
        // * Removes the original pair 'BN' from pairCounts

        // Iterate over all the pairs, and figure out what each will contribute to the counts of the *next* iteration
        for (String pair : pairCounts.keySet()) {
            // Add the new letter to letterCounts
            final Character newChar = insertions.get(pair);
            // The number of occurrences of newChar will be the number of times the pair that generates it occurs
            final Long pairCount = pairCounts.get(pair);
            letterCounts.put(newChar,
                    letterCounts.getOrDefault(newChar, 0L) + 1 * pairCount);
            // Add the new pairs to pairDeltas
            // E.g. BN -> C creates a BC & CN
            final Character char1 = pair.charAt(0);
            final Character char2 = pair.charAt(1);
            final String newPair1 = new StringBuilder().append(char1).append(newChar).toString();
            final String newPair2 = new StringBuilder().append(newChar).append(char2).toString();
            // The number of occurrences added or removed will be the number of times the pair occurs
            pairDeltas.put(newPair1,
                    pairDeltas.getOrDefault(newPair1, 0L) + 1 * pairCount);
            pairDeltas.put(newPair2,
                    pairDeltas.getOrDefault(newPair2, 0L) + 1 * pairCount);
            // And finally remove the old pair (e.g. 'BN')
            pairDeltas.put(pair, pairDeltas.getOrDefault(pair, 0L) - 1 * pairCount);
        }

        // When we're done figuring out the deltas, update the counts in pairCounts
        pairDeltas.entrySet().stream().forEach(delta -> pairCounts
                .put(delta.getKey(),
                        pairCounts.getOrDefault(delta.getKey(), 0L) + delta.getValue()));
        pairDeltas.clear();
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        chain.clear();
        try (final BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            // Read the first line (the starting chain)
            inputLine = br.readLine();
            // Make it into a LinkedList
            Arrays.stream(inputLine.split("")).forEach(c -> chain.add(c));
            br.readLine();  // skip the blank line
            final Pattern p = Pattern.compile("(\\w{2}) -> (\\w)");
            while ((line = br.readLine()) != null) {
                // process the line.
                final Matcher m = p.matcher(line);
                if (m.find()) { // If our regex matched this line
                    // Parse it
                    final String pair = m.group(1);
                    final String insertion = m.group(2);
                    insertions.put(pair, insertion.charAt(0));
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
