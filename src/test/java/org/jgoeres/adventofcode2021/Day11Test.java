package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day11.Day11Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day11Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day11/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day11Service day11Service = null;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day11/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day11Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day11() {
        if (day11Service == null) {
            day11Service = new Day11Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED = 1723;
        long result = 0;
        try {
            result = day11Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);

        final long EXPECTED_PARTB = 327;
        try {
            result = day11Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED_PARTB, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    public void Day11Example1() {
        example1Service = new Day11Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 1656;
        long result = 0;
        try {
            result = example1Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);

        final long EXPECTED_PARTB = 195;
        try {
            result = example1Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED_PARTB, result);
    }
}