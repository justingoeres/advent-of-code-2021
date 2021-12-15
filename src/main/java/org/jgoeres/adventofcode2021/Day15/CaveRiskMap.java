package org.jgoeres.adventofcode2021.Day15;

import org.jgoeres.adventofcode.common.XYPoint;
import java.util.HashMap;
import java.util.Map;

public class CaveRiskMap {
    private final Map<XYPoint, Integer> caveRisks = new HashMap();
    private Integer xSize = 0;
    private Integer ySize = 0;
    private Integer factor = 1;

    public void put(XYPoint xy, Integer risk) {
        // just a regular add to Map
        caveRisks.put(xy, risk);
        // But track the size of the grid
        if ((xy.getX() + 1) > xSize) {
            xSize = xy.getX() + 1;
        }
        if ((xy.getY() + 1) > ySize) {
            ySize = xy.getY() + 1;
        }
    }

    public Integer get(XYPoint xy) {
        // If xy is inside the puzzle input, just return it
        if (xy.getX() < xSize && xy.getY() < ySize) {
            return caveRisks.get(xy);
        }
        // Special rules for getting points beyond the size of our puzzle input
        // Get the base risk from the offset of whatever this point is, into the original grid
        final Integer baseRisk = caveRisks.get(getBaseXYPoint(xy));

        // Then bump the risk by +1 for every X and Y "rollover"
        final Integer xRollover = xy.getX() / xSize;
        final Integer yRollover = xy.getY() / ySize;

        final Integer rolloverRisk = (baseRisk + xRollover + yRollover);
        // For any value > 9, roll it over and count from 1
        final Integer correctedRisk = (rolloverRisk > 9) ? rolloverRisk - 9 : rolloverRisk;
        return correctedRisk;
    }

    public void setFactor(Integer factor) {
        this.factor = factor;
    }

    public Boolean containsKey(XYPoint xy) {
        final Integer x = xy.getX();
        final Integer y = xy.getY();
        if (x < 0 || (x > (xSize * factor) - 1) || y < 0 || (y > (ySize * factor) - 1)) {
            return false;
        } else {
            return caveRisks.containsKey(getBaseXYPoint(xy));
        }
    }

    public void clear() {
        caveRisks.clear();
        xSize = 0;
        ySize = 0;
    }

    private XYPoint getBaseXYPoint(XYPoint xy) {
        // If xy is inside the puzzle input, just return it
        if (xy.getX() < xSize && xy.getY() < ySize) {
            return xy;
        }

        // Otherwise return XY masked down into the puzzle input
        return (new XYPoint(xy.getX() % xSize, xy.getY() % ySize));
    }

    public Integer getxSize() {
        return xSize * factor;
    }

    public Integer getySize() {
        return ySize * factor;
    }
}
