package org.jgoeres.adventofcode2021.Day13;

public class Fold {
    private final FoldDirection direction;
    private final Integer coord;

    public Fold(FoldDirection direction, Integer coord) {
        this.direction = direction;
        this.coord = coord;
    }

    public FoldDirection getDirection() {
        return direction;
    }

    public Integer getCoord() {
        return coord;
    }

    enum FoldDirection {
        HORIZONTAL,
        VERTICAL
    }
}
