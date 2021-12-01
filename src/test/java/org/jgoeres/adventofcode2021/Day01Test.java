package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day01.Day01Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day01Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day01/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private final Day01Service day01Service = new Day01Service(PUZZLE_INPUT, PUZZLE_DEBUG);

    // Examples
    private final String EXAMPLE1_INPUT = "data/day01/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day01Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day01A() {
        final long EXPECTED = 1711;
        long result = 0;
        try {
            result = day01Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(2)   // Run after Puzzle Part A
    public void Day01B() {
        final long EXPECTED = 1743;
        long result = 0;
        try {
            result = day01Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }
}
