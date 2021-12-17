package org.jgoeres.adventofcode.common;

public class Area {
    private final XYPoint upperLeft;
    private final XYPoint lowerRight;

    public Area(XYPoint upperLeft, XYPoint lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
    }

    public Boolean contains(XYPoint xy) {
        final Boolean withinX = (upperLeft.getX() < xy.getX()
                && xy.getX() < lowerRight.getX());
        final Boolean withinY = (lowerRight.getY() < xy.getY()
                && xy.getY() < upperLeft.getY());
        return withinX && withinY;
    }
}
