package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day02.Day02Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day02Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day02/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private final Day02Service day02Service = new Day02Service(PUZZLE_INPUT, PUZZLE_DEBUG);

    // Examples
    private final String EXAMPLE1_INPUT = "data/day02/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day02Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day02() {
        final long EXPECTED = 1989265;
        long result = 0;
        try {
            result = day02Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);

        final long PARTB_EXPECTED = 2089174012;
        try {
            result = day02Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(PARTB_EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    @Disabled
    public void Day02Example1() {
        example1Service = new Day02Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 0;
        long result = 0;
        try {
            result = example1Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);

        final long PARTB_EXPECTED = 900;
        try {
            result = example1Service.doPartB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(PARTB_EXPECTED, result);
    }
}
