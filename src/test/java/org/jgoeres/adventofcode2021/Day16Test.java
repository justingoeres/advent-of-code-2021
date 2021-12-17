package org.jgoeres.adventofcode2021;

import org.jgoeres.adventofcode2021.Day16.Day16Service;
import org.jgoeres.adventofcode.common.ToClipboard;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Day16Test {
    // Puzzle
    private final String PUZZLE_INPUT = "data/day16/input.txt";
    private final boolean PUZZLE_DEBUG = false;
    private Day16Service day16Service = null;

    // Examples
    private final String EXAMPLE1_INPUT = "data/day16/example1.txt";
    private final boolean EXAMPLE_DEBUG = false;
    private Day16Service example1Service = null;

    // Examples
    private final String EXAMPLE2_INPUT = "data/day16/example2.txt";
    private Day16Service example2Service = null;

    // Examples
    private final String EXAMPLE3_INPUT = "data/day16/example3.txt";
    private Day16Service example3Service = null;

    @Test
    @Order(1)   // Run before Puzzle Part B
    public void Day16() {
        if (day16Service == null) {
            day16Service = new Day16Service(PUZZLE_INPUT, PUZZLE_DEBUG);
        }

        final long EXPECTED_PARTA = 974;
        long result = 0;
        try {
            result = day16Service.doPartA();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED_PARTA, result);


        final long EXPECTED_PARTB = 180616437720L;
        try {
            result = day16Service.doPartB();
            ToClipboard.set(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED_PARTB, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    public void Day16AExample1() {
        example1Service = new Day16Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 6;
        long result = 0;
        try {
            result = example1Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    @Disabled
    public void Day16AExample2() {
        example2Service = new Day16Service(EXAMPLE2_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 0;
        long result = 0;
        try {
            result = example2Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    @Disabled
    public void Day16AExample3() {
        example3Service = new Day16Service(EXAMPLE3_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 0;
        long result = 0;
        try {
            result = example3Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    public void Day16AExample4() {
        final String EXAMPLE4_INPUT = "data/day16/example4.txt";

        Day16Service example4Service = new Day16Service(EXAMPLE4_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 16;
        long result = 0;
        try {
            result = example4Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    public void Day16AExample5() {
        final String EXAMPLE5_INPUT = "data/day16/example5.txt";

        Day16Service example5Service = new Day16Service(EXAMPLE5_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 12;
        long result = 0;
        try {
            result = example5Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(3)   // Run before Example Part B
    public void Day16AExample6() {
        final String EXAMPLE6_INPUT = "data/day16/example6.txt";

        Day16Service example6Service = new Day16Service(EXAMPLE6_INPUT, EXAMPLE_DEBUG);
        final long EXPECTED = 23;
        long result = 0;
        try {
            result = example6Service.doPartA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    @Order(4)   // Run after Example Part A
    @Disabled
    public void Day16BExample1() {
        // Instantiate the service if Part A was skipped
        if (example1Service == null) {
            example1Service = new Day16Service(EXAMPLE1_INPUT, EXAMPLE_DEBUG);
        }

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
