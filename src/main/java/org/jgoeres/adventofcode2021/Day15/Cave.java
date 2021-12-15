package org.jgoeres.adventofcode2021.Day15;

import org.jgoeres.adventofcode.common.XYPoint;

public class Cave {
    private final XYPoint xy;
    private Integer totalRisk;

    public Cave(XYPoint xy) {
        this.xy = xy;
    }

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

    public void setTotalRisk(Integer totalRisk) {
        this.totalRisk = totalRisk;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
