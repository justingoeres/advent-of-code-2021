package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day13.Day13Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day13Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day13/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day13Service day13Service = null;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day13/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day13Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day13A() {
        if (day13Service == null) {
            day13Service = new Day13Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED = 759;
        long result = 0;
        try {
            result = day13Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(2)   // Run after Puzzle Part A
    public void Day13B() {
        if (day13Service == null) {
            day13Service = new Day13Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        try {
            day13Service.doPartB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // There is no test for 13B, it just has to spell "HECRZKPR"
    }

    @Test
    @Order(3)   // Run before Example Part B
    @Disabled
    public void Day13AExample1() {
        example1Service = new Day13Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
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
    public void Day13BExample1() {
        // Instantiate the service if Part A was skipped
        if (example1Service == null) {
            example1Service = new Day13Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        }

        final long EXPECTED = 0;
        long result = 0;
        try {
            example1Service.doPartB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }
}
