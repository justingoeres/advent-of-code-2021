package org.jgoeres.adventofcode2021.Day02;

public class Submarine2DStep {
    final String direction;
    final Integer distance;

    public Submarine2DStep(String direction, Integer distance) {
        this.direction = direction;
        this.distance = distance;
    }

    public String getDirection() {
        return direction;
    }

    public Integer getDistance() {
        return distance;
    }
}
