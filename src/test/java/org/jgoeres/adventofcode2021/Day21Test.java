package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day21.Day21Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day21Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day21/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day21Service day21Service = null;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day21/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day21Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day21A() {
        if (day21Service == null) {
            day21Service = new Day21Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED = 678468;
        long result = 0;
        try {
            result = day21Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(2)   // Run after Puzzle Part A
    public void Day21B() {
        if (day21Service == null) {
            day21Service = new Day21Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED = 0;
        long result = 0;
        try {
            result = day21Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    @Disabled
    public void Day21AExample1() {
        example1Service = new Day21Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 739785;
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
    public void Day21BExample1() {
        // Instantiate the service if Part A was skipped
        if (example1Service == null) example1Service = new Day21Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);

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
