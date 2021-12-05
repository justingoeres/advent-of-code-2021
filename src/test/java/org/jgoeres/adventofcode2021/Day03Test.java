package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day03.Day03Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day03Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day03/input.txt";
    private final boolean PUZZLE_DEBUG = false;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day03/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day03Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day03() {
        final Day03Service day03Service = new Day03Service(PUZZLE_INPUT, PUZZLE_DEBUG);

        final long PARTA_EXPECTED = 1997414;
        long result = 0;
        try {
            result = day03Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(PARTA_EXPECTED, result);

        final long PARTB_EXPECTED = 1032597;
        try {
            result = day03Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(PARTB_EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    public void Day03AExample1() {
        example1Service = new Day03Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 198;
        long result = 0;
        try {
            result = example1Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(4)   // Run after Example Part A
    @Disabled
    public void Day03BExample1() {
        // Instantiate the service if Part A was skipped
        if (example1Service == null) example1Service = new Day03Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);

        final long EXPECTED = 230;
        long result = 0;
        try {
            result = example1Service.doPartB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }
}
