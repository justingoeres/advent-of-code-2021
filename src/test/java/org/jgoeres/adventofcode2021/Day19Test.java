package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day19.Day19Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day19Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day19/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day19Service day19Service = null;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day19/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day19Service example1Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day19() {
        if (day19Service == null) {
            day19Service = new Day19Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED_PARTA = 512;
        long result = 0;
        try {
            result = day19Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        assertEquals(EXPECTED_PARTA, result);

        final long EXPECTED_PARTB = 16802;
        try {
            result = day19Service.doPartB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED_PARTB, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    public void Day19Example1() {
        example1Service = new Day19Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED_PARTA = 79;
        long result = 0;
        try {
            result = example1Service.doPartA();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        assertEquals(EXPECTED_PARTA, result);

        final long EXPECTED_PARTB = 3621;
        try {
            result = example1Service.doPartB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED_PARTB, result);
    }
}
