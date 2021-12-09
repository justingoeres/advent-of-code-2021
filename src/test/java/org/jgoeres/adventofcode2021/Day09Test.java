package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day09.Day09Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day09Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day09/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day09Service day09Service = null;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day09/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day09Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day09() {
        if (day09Service == null) {
            day09Service = new Day09Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        // Part A
        final long PARTA_EXPECTED = 550;
        long result = 0;
        try {
            result = day09Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(PARTA_EXPECTED, result);

        // Part B
        final long PARTB_EXPECTED = 1100682;
        try {
            result = day09Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(PARTB_EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    public void Day09Example1() {
        example1Service = new Day09Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long PARTA_EXPECTED = 15;
        long result = 0;
        try {
            result = example1Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(PARTA_EXPECTED, result);

        final long PARTB_EXPECTED = 1134;
        try {
            result = example1Service.doPartB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(PARTB_EXPECTED, result);
    }
}
