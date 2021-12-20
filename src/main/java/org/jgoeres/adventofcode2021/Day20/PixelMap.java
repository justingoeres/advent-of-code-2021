package org.jgoeres.adventofcode2021.Day20;

import org.jgoeres.adventofcode.common.XYPoint;
import java.util.HashSet;
import java.util.Set;

public class PixelMap {
    public static final Character PIXEL_ON = '#';
    public static final Character PIXEL_OFF = '.';

    private final XYPoint toCheck = new XYPoint();

    private final Set<XYPoint> map = new HashSet<>();

    private Integer xMin = Integer.MAX_VALUE;
    private Integer yMin = Integer.MAX_VALUE;
    private Integer xMax = Integer.MIN_VALUE;
    private Integer yMax = Integer.MIN_VALUE;

    public boolean add(final XYPoint xy) {
        // Add the new point to the map, and update the map extents
        if (map.add(xy)) {
            // If 'xy' was successfully added to the set, update the extents.
            checkExtents(xy);
            return true;
        }
        return false;
    }

    public boolean contains(final XYPoint xy) {
        return map.contains(xy);
    }

    public void clear() {
        map.clear();
    }

    private void updateAllExtents() {
        // Look through all the known pixels and find the x & y extents
        map.stream().forEach(xy -> checkExtents(xy));
    }

    private void checkExtents(final XYPoint xy) {
        if (xy.getX() < xMin) {
            xMin = xy.getX();
        }
        if (xy.getX() > xMax) {
            xMax = xy.getX();
        }
        if (xy.getY() < yMin) {
            yMin = xy.getY();
        }
        if (xy.getY() > yMax) {
            yMax = xy.getY();
        }
    }

    public Set<XYPoint> getMap() {
        return map;
    }

    public Integer getxMin() {
        return xMin;
    }

    public Integer getyMin() {
        return yMin;
    }

    public Integer getxMax() {
        return xMax;
    }

    public Integer getyMax() {
        return yMax;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();

        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                toCheck.set(x, y);
                sb.append(map.contains(toCheck) ? PIXEL_ON : PIXEL_OFF);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
