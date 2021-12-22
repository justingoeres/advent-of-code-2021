package org.jgoeres.adventofcode2021.Day21;

import java.util.Objects;

public class GameState {
    private static final Integer TARGET_SCORE_PART_B = 21;
    private final Player player1;
    private final Player player2;

    public GameState(Integer p1, Integer p2) {
        this.player1 = new Player(1, p1, TARGET_SCORE_PART_B);
        this.player2 = new Player(2, p2, TARGET_SCORE_PART_B);
    }

    public GameState(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Boolean isOver() {
        return (player1.hasWon() || player2.hasWon());
    }

    public GameState clone() {
        // return a new game state identical to this one
        return new GameState(player1.clone(), player2.clone());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameState gameState = (GameState) o;
        return Objects.equals(player1, gameState.player1) &&
                Objects.equals(player2, gameState.player2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1, player2);
    }
}
