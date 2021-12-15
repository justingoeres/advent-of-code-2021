package org.jgoeres.adventofcode2021.Day15;

import org.jgoeres.adventofcode.common.XYPoint;

public class Cave {
    private final XYPoint xy;
    private final Integer totalRisk;

    public Cave(XYPoint xy, Integer totalRisk) {
        this.xy = xy;
        this.totalRisk = totalRisk;
    }

    public XYPoint getXy() {
        return xy;
    }

    public Integer getTotalRisk() {
        return totalRisk;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
