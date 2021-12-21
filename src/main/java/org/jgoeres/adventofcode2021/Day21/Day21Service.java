package org.jgoeres.adventofcode2021.Day21;

import org.jgoeres.adventofcode.common.Utils.Pair;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21Service {
    public boolean DEBUG = false;

    private ArrayList<Player> players = new ArrayList<>();
    private Pair<Player> playerPair;

    public Day21Service(String pathToFile) {
        loadInputs(pathToFile);
    }

    public Day21Service(String pathToFile, boolean debug) {
        loadInputs(pathToFile);
        DEBUG = debug;
    }

    public long doPartA() {
        System.out.println("=== DAY 21A ===");

        long result = 0;
        /**
         * Play a practice game using the deterministic 100-sided die.
         **/

        Integer die = 0;    // we add before we roll, so start at zero
        Integer rolls = 0;
        while (true) {  // go until the game ends
            Player player = currentPlayer();
            // On each player's turn, the player rolls the die three times and adds up the results.
            for (int i = 1; i <= 3; i++) {
                die++;
                die = ((die - 1) % 100) + 1; // loop the die after 100 sides
                rolls++;
                player.doRoll(die);
            }
            // Then, the player moves their pawn that many times forward around the track
            // After each player moves, they increase their score by the value of the space
            // their pawn stopped on.
            player.finishTurn();

            // Is the game over?
            if (player.hasWon()) {
                break;
            } else {
                // swap players and continue
                playerPair.swap();
            }
        }
        /**
         * The moment either player wins, what do you get if you multiply
         * the score of the losing player by the number of times the die
         * was rolled during the game?
         **/
        Player loser = playerPair.getSecond();  // The player who DIDN'T just win

        System.out.println(
                MessageFormat.format("Player {0} loses with a score of {1} after {2} rolls.",
                        loser.getId(), loser.getScore(), rolls));
        result = loser.getScore() * rolls;

        System.out.println("Day 21A: Answer = " + result);
        return result;
    }

    private Player currentPlayer() {
        return playerPair.getFirst();
    }

    public long doPartB() {
        System.out.println("=== DAY 21B ===");

        long result = 0;
        /** Put problem implementation here **/

        System.out.println("Day 21B: Answer = " + result);
        return result;
    }

    // load inputs line-by-line and apply a regex to extract fields
    private void loadInputs(String pathToFile) {
        players.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            Pattern p = Pattern.compile("Player \\d starting position: (\\d+)");
            // process both lines (there are only two).
            String line = br.readLine();
            Matcher m = p.matcher(line);
            m.find();
            // player 1
            final Integer p1Start = Integer.parseInt(m.group(1));
            final Player p1 = new Player(1, p1Start);
            players.add(p1);

            line = br.readLine();
            m = p.matcher(line);
            m.find();
            // player 2
            Integer p2Start = Integer.parseInt(m.group(1));
            final Player p2 = new Player(2, p2Start);
            players.add(p2);

            playerPair = new Pair<>(p1, p2);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
