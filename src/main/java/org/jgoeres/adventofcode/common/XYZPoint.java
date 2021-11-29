package org.jgoeres.adventofcode.common;

import java.util.Objects;

public class XYZPoint extends XYPoint {
    private int z = 0;

    public XYZPoint(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public XYZPoint() {
        // Create at 0, 0, 0
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return (getX() + ", " + getY() + ", " + getZ());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof XYZPoint))
            return false;
        if (obj == this)
            return true;
        // Two points are equivalent if they have the same coordinates
        return ((this.getX() == ((XYZPoint) obj).getX())
                && (this.getY() == ((XYZPoint) obj).getY())
                && (this.getZ() == ((XYZPoint) obj).getZ()));
    }

    @Override
    public int hashCode() {
        // Make the hash code things like (3,4,5) -> 500040003
        return (Objects.hash(getX(), getY(), z));
    }
}
