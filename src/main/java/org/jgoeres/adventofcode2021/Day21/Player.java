package org.jgoeres.adventofcode2021.Day21;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final Integer targetScore;

    private final Integer id;
    private Integer score = 0;
    private Integer position;

    private List<Integer> rolls = new ArrayList<>();
    private Integer rollTotal = 0;

    public Player(final Integer id, final Integer position) {
        this.id = id;
        this.position = position;
        this.targetScore = 1000;
    }

    public Player(Integer id, Integer position, Integer targetScore) {
        this.targetScore = targetScore;
        this.id = id;
        this.position = position;
    }

    public Player(Integer id, Integer position, Integer score, Integer targetScore) {
        this.targetScore = targetScore;
        this.id = id;
        this.score = score;
        this.position = position;
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

    public Integer getPosition() {
        return position;
    }

    public Player clone() {
        // Return a new player identical to this one
        return new Player(this.id, this.position, this.score, this.targetScore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return targetScore.equals(player.targetScore) && id.equals(player.id) &&
                score.equals(player.score) && position.equals(player.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetScore, id, score, position);
    }
}
