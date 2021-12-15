package org.jgoeres.adventofcode2021.Day15;

import java.util.Comparator;

public class CaveReverseRiskComparator implements Comparator<Cave> {
    @Override
    public int compare(Cave o1, Cave o2) {
        // Sort by total risk, but from *low to high*
        if (o1.getTotalRisk() > o2.getTotalRisk()) {
            return 1;
        } else if (o1.getTotalRisk() == o2.getTotalRisk()) {
            return 0;
        } else {
            return -1;
        }
    }
}
