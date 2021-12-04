package org.jgoeres.adventofcode2021.Day04;

public class BingoSpot {
    private final Integer value;
    private Boolean marked = false;

    public BingoSpot(Integer value) {
        this.value = value;
    }

    public Boolean getMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }
}
