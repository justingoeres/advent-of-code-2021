package org.jgoeres.adventofcode2021.Day12;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Journey {
    private Cave current;
    private final Set<Cave> visited = new HashSet<>();

    public Journey(Cave current) {
        this.current = current;
    }

    public void goTo(Cave cave) {
        // Add the current cave to places we've "visited"
        this.addVisited(getCurrent());
        // Then move to the new cave
        this.current = cave;
    }

    private void addVisited(Cave cave) {
        // Only add 'small' caves to the visited list, since we can visit large ones over and over
        if (cave.isSmall()) {
            visited.add(cave);
        }
    }

    public Boolean hasVisited(Cave cave) {
        return visited.contains(cave);
    }

    public Set<Cave> canVisit() {
        // This journey "can visit" any of its neighbors
        final Set<Cave> canVisit = current.getAllNeighbors();
        // EXCEPT small caves it has already been to
        canVisit.removeAll(visited);
        return canVisit;
    }

    public Boolean isFinished() {
        // Are we at the end?
        return (current.getName() == "end");
    }

    public Cave getCurrent() {
        return current;
    }

    public Set<Cave> getVisited() {
        return visited;
    }

    @Override
    public String toString() {
        return visited.stream().map(cave -> cave.getName())
                .collect(Collectors.joining(", "))
                + ", " + current.getName();
    }
}
