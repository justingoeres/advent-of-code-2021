package org.jgoeres.adventofcode2021.Day12;

import org.apache.commons.lang3.StringUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cave {
    private final String name;

    private final Set<Cave> smallNeighbors = new HashSet<>();
    private final Set<Cave> bigNeighbors = new HashSet<>();

    public Cave(String name) {
        this.name = name;
    }

    public Boolean isSmall() {
        return StringUtils.isAllLowerCase(this.getName());
    }

    public void addSmallNeighbor(Cave smallNeighbor) {
        this.getSmallNeighbors().add(smallNeighbor);
    }

    public void addBigNeighbor(Cave bigNeighbor) {
        this.getBigNeighbors().add(bigNeighbor);
    }

    public void addBigOrSmallNeighbor(Cave neighbor) {
        // Figure out if the new neighbor is big or small,
        // and add it to the corresponding set.
        if (neighbor.isSmall()) {
            // small
            this.addSmallNeighbor(neighbor);
        } else {
            // big
            this.addBigNeighbor(neighbor);
        }
    }

    public String getName() {
        return name;
    }

    public Set<Cave> getSmallNeighbors() {
        return smallNeighbors;
    }

    public Set<Cave> getBigNeighbors() {
        return bigNeighbors;
    }

    public Set<Cave> getAllNeighbors() {
        return Stream.concat(smallNeighbors.stream(), bigNeighbors.stream())
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return name +
                " -> " + smallNeighbors.stream().map(cave -> cave.getName()).collect(
                Collectors.joining(", ")) +
                " / " + bigNeighbors.stream().map(cave -> cave.getName()).collect(
                Collectors.joining(", "));
    }
}
