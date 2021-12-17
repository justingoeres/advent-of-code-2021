package org.jgoeres.adventofcode2021.Day17;

import org.jgoeres.adventofcode.common.XYPoint;

public class Probe {
    private XYPoint xy;
    private Integer xVelocity;
    private Integer yVelocity;

    public Probe(XYPoint xy, Integer initialXVelocity, Integer initialYVelocity) {
        this.xy = xy;
        this.xVelocity = initialXVelocity;
        this.yVelocity = initialYVelocity;
    }
    

}
