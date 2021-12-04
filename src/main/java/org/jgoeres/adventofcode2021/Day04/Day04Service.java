package org.jgoeres.adventofcode2021.Day04;

import org.jgoeres.adventofcode.common.XYPoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04Service {
    public boolean DEBUG = false;

    private List<Integer> drawOrder = new ArrayList<>();
    private List<BingoCard> bingoCards = new ArrayList<>();

    public Day04Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day04Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 4A ===");

        long result = 0;
        /** Play the game:
         * 1. Draw balls in order
         * 2. For each ball, mark all the cards
         * 3. After marking, check all cards for winners
         * 4. If a winner is found, calculate the score.
         * **/

        // Draw balls
        for (Integer ball : drawOrder) {
//            System.out.println("Drawing ball:\t" + ball);
                // Mark cards
            for (BingoCard card : bingoCards) {
                // Check each card for the presence of a spot
                if (card.hasValue(ball)) {
                    // Mark it!
                    card.setMarked(ball);
//                    card.printCard();
                    // Then check if this card is a winner
                    if (card.isWinner()) {
                        // calculate its score!
                        result = card.calculateScore(ball);
                        // and we're done!
                        System.out.println("Day 4A: Answer = " + result);
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public long doPartB() {
        System.out.println("=== DAY 4B ===");

        long result = 0;
        /** Let the squid win!
         * Figure out which board will win LAST
         **/


        System.out.println("Day 4B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        drawOrder.clear();
        bingoCards.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            // Read the first line to get all the numbers drawn
            line = br.readLine();
            drawOrder = Arrays.stream(line.split(","))
                    .map(n -> Integer.parseInt(n)).collect(Collectors.toList());

            // Now build up the cards
            BingoCard newCard = null;
            Integer row = 0;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {   // Empty line -> start a new card
                    // If our "new card" is NOT null here, then it's complete and we should add
                    // it to the cards list before starting a new card
                    if (newCard != null) {
                        newCard.setSize(
                                row);   // now that we've done all the rows we know how big the card is
                        bingoCards.add(newCard);
                    }
                    newCard = new BingoCard();
                    row = 0;
                } else {
                    // leftmost column is 0
                    addRowToCard(newCard, row,
                            Arrays.stream(line.trim().split("\\s+"))
                                    .map(n -> Integer.parseInt(n)).collect(Collectors.toList())
                    );
                    // increment the row and continue!
                    row++;
                }
            }
            // After we hit the end of the file, we still need to add the last "new card"
            // into the set of cards
            newCard.setSize(row);
            bingoCards.add(newCard);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    private void addRowToCard(BingoCard card, Integer row, List<Integer> values) {
        for (int column = 0; column < values.size(); column++) {
            card.addSpot(new XYPoint(row, column), values.get(column));
        }
    }
}
