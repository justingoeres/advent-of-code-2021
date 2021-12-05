package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day05.Day05Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day05Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day05/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private final Day05Service day05Service = new Day05Service(PUZZLE_INPUT, PUZZLE_DEBUG);

    // Examples
    private final String EXAMPLE1_INPUT = "data/day05/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day05Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day05A() {
        final long EXPECTED = 6461;
        long result = 0;
        try {
            result = day05Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(2)   // Run after Puzzle Part A
    public void Day05B() {
        final long EXPECTED = 18065;
        long result = 0;
        try {
            result = day05Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    @Disabled
    public void Day05AExample1() {
        example1Service = new Day05Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 0;
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
    public void Day05BExample1() {
        // Instantiate the service if Part A was skipped
        if (example1Service == null) example1Service = new Day05Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);

        final long EXPECTED = 0;
        long result = 0;
        try {
            result = example1Service.doPartB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }
}
