package org.jgoeres.adventofcode2021.Day21;

import org.jgoeres.adventofcode.common.Utils.Pair;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21Service {
    public boolean DEBUG = false;

    private ArrayList<Player> players = new ArrayList<>();
    private Pair<Player> playerPair;
    private Integer p1Start;
    private Integer p2Start;

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

        Long player1WinCount = 0L;
        Long player2WinCount = 0L;

        /**
         * Now use a quantum die: when you roll it, the universe splits into multiple copies,
         * one copy for each possible outcome of the die. In this case, rolling the die always
         * splits the universe into three copies: one where the outcome of the roll was 1,
         * one where it was 2, and one where it was 3.
         **/

        // Create the initial game state (and placeholder for nextGameState)
        GameState initialGameState = new GameState(p1Start, p2Start);
        Map<GameState, Long> gameStateCounts = new HashMap<>();
        Map<GameState, Long> nextGameStateCounts = new HashMap<>();
        Pair<Map<GameState, Long>> gameStatePair = new Pair(gameStateCounts, nextGameStateCounts);

        // There's one of it
        gameStateCounts.put(initialGameState, 1L);

        while (!gameStateCounts.isEmpty()) {
            // Process a turn
            nextGameStateCounts.clear();
            // Seed the nextGameState
            // For each existing game state
            for (Map.Entry<GameState, Long> gameStateEntry : gameStateCounts.entrySet()) {
                // Player 1 rolls (and creates 27 *new* (cloned) game states)
                for (Map.Entry<Integer, Long> newPlayerState : diracRollCounts.entrySet()) {
                    GameState gameState = gameStateEntry.getKey().clone();
                    Long currentCount = gameStateCounts.get(gameState);
                    // There are 27 of these
                    // Update player1's score & position
                    Integer rollTotal = newPlayerState.getKey();
                    Long rollCount = newPlayerState.getValue();
                    gameState.getPlayer1().doRoll(rollTotal);   // add all 3 rolls as one
                    gameState.getPlayer1().finishTurn();    // process to update the game state

                    // The count of this new gameState in the nextGameStateCounts is
                    // The number of counts we ALREADY have for this state
                    // TIMES the number of new ones (rollCount) we just created
                    // (PLUS the number of this state we may have ALREADY created in this loop!)
                    Long newGameStateCount = nextGameStateCounts.getOrDefault(gameState, 0L)
                            + currentCount * rollCount;
                    // Did player 1 win?
                    if (gameState.getPlayer1().hasWon()) {
                        // DON'T put this game state in nextGameStates because we don't care about it anymore!
                        // Just add the total to p1's win count
                        player1WinCount += newGameStateCount;
                    } else {
                        // Stick the new value in nextGameStateCounts
                        nextGameStateCounts.put(gameState, newGameStateCount);
                    }
                }
            }

            // When player 1 is done, swap the gameStates for player 2's turn
            gameStatePair.swap();
            gameStateCounts = gameStatePair.getFirst();
            nextGameStateCounts = gameStatePair.getSecond();
            // Clear the nextGameStateCounts so we can populate with the new player results
            nextGameStateCounts.clear();

            for (Map.Entry<GameState, Long> gameStateEntry : gameStateCounts.entrySet()) {
                // Player 2 rolls (and creates 27 *new* (cloned) game states)
                for (Map.Entry<Integer, Long> newPlayerState : diracRollCounts.entrySet()) {
                    GameState gameState = gameStateEntry.getKey().clone();
                    Long currentCount = gameStateCounts.get(gameState);
                    // There are 27 of these
                    // Update player2's score & position
                    Integer rollTotal = newPlayerState.getKey();
                    Long rollCount = newPlayerState.getValue();
                    gameState.getPlayer2().doRoll(rollTotal);   // add all 3 rolls as one
                    gameState.getPlayer2().finishTurn();    // process to update the game state

                    // The count of this new gameState in the nextGameStateCounts is
                    // The number of counts we ALREADY have for this state
                    // TIMES the number of new ones (rollCount) we just created
                    // (PLUS the number of this state we may have ALREADY created in this loop!)
                    Long newGameStateCount = nextGameStateCounts.getOrDefault(gameState, 0L)
                            + currentCount * rollCount;
                    // Did player 2 win?
                    if (gameState.getPlayer2().hasWon()) {
                        // DON'T put this game state in nextGameStates because we don't care about it anymore!
                        // Just add the total to p2's win count
                        player2WinCount += newGameStateCount;
                    } else {
                        // Stick the new value in nextGameStateCounts
                        nextGameStateCounts.put(gameState, newGameStateCount);
                    }
                }
            }
            // When player 2 is done, swap the gameStates for player 1's next turn
            gameStatePair.swap();
            gameStateCounts = gameStatePair.getFirst();
            nextGameStateCounts = gameStatePair.getSecond();
        }

        /**
         * Using your given starting positions, determine every possible outcome.
         * Find the player that wins in more universes; in how many universes does that player win?
         **/
        long result = Math.max(player1WinCount, player2WinCount);
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
            p1Start = Integer.parseInt(m.group(1));
            final Player p1 = new Player(1, p1Start);
            players.add(p1);

            line = br.readLine();
            m = p.matcher(line);
            m.find();
            // player 2
            p2Start = Integer.parseInt(m.group(1));
            final Player p2 = new Player(2, p2Start);
            players.add(p2);

            playerPair = new Pair<>(p1, p2);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    // Static map of roll totals (3-9) and how often they each occur in a series of 3 rolls
    private static final Map<Integer, Long> diracRollCounts = Stream.of(
                    new SimpleImmutableEntry<>(3, 1L),
                    new SimpleImmutableEntry<>(4, 3L),
                    new SimpleImmutableEntry<>(5, 6L),
                    new SimpleImmutableEntry<>(6, 7L),
                    new SimpleImmutableEntry<>(7, 6L),
                    new SimpleImmutableEntry<>(8, 3L),
                    new SimpleImmutableEntry<>(9, 1L))
            .collect(
                    Collectors.toMap(SimpleImmutableEntry::getKey, SimpleImmutableEntry::getValue));
}
