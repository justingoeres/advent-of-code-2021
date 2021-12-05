package org.jgoeres.adventofcode2021.Day05;

import org.jgoeres.adventofcode.common.XYPoint;

public class VentDefinition {
    private final XYPoint start;
    private final XYPoint end;
    private final Direction direction;

    public VentDefinition(int x1, int y1, int x2, int y2) {
        this.start = new XYPoint(x1, y1);
        this.end = new XYPoint(x2, y2);
        if (x1 == x2) {
            this.direction = Direction.VERTICAL;
        } else if (y1 == y2) {
            this.direction = Direction.HORIZONTAL;
        } else {
            this.direction = Direction.DIAGONAL;
        }
    }

    public XYPoint getStart() {
        return start;
    }

    public XYPoint getEnd() {
        return end;
    }

    public Direction getDirection() {
        return direction;
    }
}

enum Direction {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL
}
