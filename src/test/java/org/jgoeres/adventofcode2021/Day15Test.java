package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day15.Day15Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day15Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day15/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day15Service day15Service = null;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day15/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day15Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day15A() {
        if (day15Service == null) {
            day15Service = new Day15Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED = 393;
        long result = 0;
        try {
            result = day15Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(2)   // Run after Puzzle Part A
    public void Day15B() {
        if (day15Service == null) {
            day15Service = new Day15Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED = 2823;
        long result = 0;
        try {
            result = day15Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    @Disabled
    public void Day15AExample1() {
        example1Service = new Day15Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 40;
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
    public void Day15BExample1() {
        // Instantiate the service if Part A was skipped
        if (example1Service == null) example1Service = new Day15Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);

        final long EXPECTED = 315;
        long result = 0;
        try {
            result = example1Service.doPartB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }
}
