package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day17.Day17Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day17Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day17/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day17Service day17Service = null;

    // Part A is done with just plain old math, so no test

    @Test
    @Order(2)   // Run after Puzzle Part A
    public void Day17B() {
        if (day17Service == null) {
            day17Service = new Day17Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED = 1806;
        long result = 0;
        try {
            result = day17Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }
}
