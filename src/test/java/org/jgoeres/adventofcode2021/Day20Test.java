package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day20.Day20Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day20Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day20/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day20Service day20Service = null;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day20/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day20Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day20A() {
        if (day20Service == null) {
            day20Service = new Day20Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED = 0;
        long result = 0;
        try {
            result = day20Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(2)   // Run after Puzzle Part A
    public void Day20B() {
        if (day20Service == null) {
            day20Service = new Day20Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED = 0;
        long result = 0;
        try {
            result = day20Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    @Disabled
    public void Day20AExample1() {
        example1Service = new Day20Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 35;
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
    public void Day20BExample1() {
        // Instantiate the service if Part A was skipped
        if (example1Service == null) example1Service = new Day20Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);

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
