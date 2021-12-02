package org.jgoeres.adventofcode2021.Day02;

import static org.jgoeres.adventofcode.common.DirectionURDL.*;
import org.jgoeres.adventofcode.common.XYPoint;

public class Submarine2DLocation {
    private XYPoint location;

    public Submarine2DLocation() {
        location = new XYPoint();   // defaults to 0,0
    }

    public void move(String direction, Integer distance) {
        switch (direction) {
            case "forward":
                location.moveRelative(distance, RIGHT); // Define "positive horizontal" as RIGHT
                break;
            case "down":
                location.moveRelative(distance,DOWN); // positive Y is DOWN
                break;
            case "up":
                location.moveRelative(distance,UP);
                break;
        }
    }

    public XYPoint getLocation() {
        return location;
    }
}
