package org.jgoeres.adventofcode2021.Day15;

import java.util.Comparator;

public class CaveReverseRiskComparator implements Comparator<Cave> {
    @Override
    public int compare(Cave o1, Cave o2) {
//        return (o1.getTotalRisk() - o2.getTotalRisk());
        if (o1.getTotalRisk() > o2.getTotalRisk()) {
            return 1;
        } else if (o1.getTotalRisk() == o2.getTotalRisk()) {
            return 0;
        } else {
            return -1;
        }
    }

//    @Override
//    public Comparator<Cave> reversed() {
//        return Comparator.super.reversed();
//    }
}
