package org.jgoeres.adventofcode2021.Day19;

import org.jgoeres.adventofcode.common.XYZPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Scanner {
    private final List<XYZPoint> beacons = new ArrayList<>();
    private XYZPoint position;

    public Boolean add(XYZPoint xyz) {
        return beacons.add(xyz);
    }

    public Boolean contains(XYZPoint xyz) {
        return beacons.contains(xyz);
    }

    public List<XYZPoint> getBeaconsToReference(final XYZPoint reference) {
        // Return a list of XYZPoints (our beacons) translated so that they're relative to the 'reference' point
        final List<XYZPoint> beaconsToReference = XYZPoint.getXYZToReference(beacons, reference);
        return beaconsToReference;
    }

    public XYZPoint getPosition() {
        return position;
    }

    public void setPosition(XYZPoint position) {
        this.position = position;
    }

    public List<XYZPoint> getBeacons() {
        return beacons;
    }
}
