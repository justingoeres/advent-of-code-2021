package org.jgoeres.adventofcode2021.Day19;

import org.jgoeres.adventofcode.common.XYZPoint;
import java.util.HashSet;
import java.util.Set;

public class Scanner {
    private final Set<XYZPoint> beacons = new HashSet<>();

    public Boolean add(XYZPoint xyz) {
        return beacons.add(xyz);
    }

    public Boolean contains(XYZPoint xyz) {
        return beacons.contains(xyz);
    }

    public Set<XYZPoint> getBeacons() {
        return beacons;
    }
}
