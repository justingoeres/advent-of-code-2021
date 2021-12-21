package org.jgoeres.adventofcode2021.Day21;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private static final Integer targetScore = 1000;

    private Integer id;
    private final Integer initialPosition;
    private Integer score = 0;
    private Integer position;

    private List<Integer> rolls = new ArrayList<>();
    private Integer rollTotal = 0;

    public Player(final Integer id, final Integer initialPosition) {
        this.id = id;
        this.initialPosition = initialPosition;
        this.position = initialPosition;
    }

    public void doRoll(final Integer roll) {
        // Add the new roll to the running total
        rollTotal += roll;
        rolls.add(roll);
    }

    public void finishTurn() {
        // To finish a turn, move the pawn forward by rollTotal
        // looping around from 10
//        position = (position + rollTotal) % 10;
        position += rollTotal;  // add
        if (position > 10) {
            // loop
            position = ((position - 1) % 10) + 1;
        }
        // Add the final position to the score
        score += position;

//        System.out.println(
//                MessageFormat.format(
//                        "Player {0} rolls {1} and moves to space {2} for a total score of {3}.",
//                        id, rolls.stream().map(i -> i.toString()).collect(Collectors.joining("+")),
//                        position, score));
        rollTotal = 0;  // Reset the rolls for next turn
        rolls.clear();
    }

    public Boolean hasWon() {
        return score >= targetScore;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getId() {
        return id;
    }
}
