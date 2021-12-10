package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day10.Day10Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day10Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day10/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day10Service day10Service = null;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day10/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day10Service example1Service = null;

    @Test
    public void Day10() {
        day10Service = new Day10Service(PUZZLE_INPUT, PUZZLE_DEBUG);

        final long EXPECTED = 216297;
        long result = 0;
        try {
            result = day10Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);

        final long EXPECTED_PARTB = 2165057169L;
        try {
            result = day10Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED_PARTB, result);
    }

    @Test
    public void Day10Example1() {
        example1Service = new Day10Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 26397;
        long result = 0;
        try {
            result = example1Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);

        final long EXPECTED_PARTB = 288957L;
        try {
            result = example1Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED_PARTB, result);
    }

}
