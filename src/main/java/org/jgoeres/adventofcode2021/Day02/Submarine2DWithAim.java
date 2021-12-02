package org.jgoeres.adventofcode2021.Day02;

public class Submarine2DWithAim extends Submarine2DLocation {
    private Integer aim = 0;

    @Override
    public void move(String direction, Integer distance) {
        switch (direction) {
            case "forward":
                // forward X does two things:
                // - It increases your horizontal position by X units.
                super.move("forward", distance);
                // - It increases your depth by your aim multiplied by X.
                super.move("down",aim * distance);
                break;
            // - down X increases your aim by X units.
            // - up X decreases your aim by X units.
            case "down":
                aim += distance;
                break;
            case "up":
                aim -= distance;
                break;
        }
    }
}
