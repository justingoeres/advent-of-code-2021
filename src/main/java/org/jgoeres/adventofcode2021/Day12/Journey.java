package org.jgoeres.adventofcode2021.Day12;

import java.util.*;
import java.util.stream.Collectors;

public class Journey {
    private final Map<String, Cave> allCaves;
    private Cave current;
    private final List<Cave> visited;
    private final Stack<Cave> visitedStack;

    private Integer pathsFound = 0;

    public Journey(Cave current, Map<String, Cave> allCaves) {
        this.current = current;
        this.visited = new ArrayList<>();
        this.visitedStack = new Stack<>();
        this.allCaves = allCaves;
    }

    public Journey(Cave current, List<Cave> visited) {
        this.current = current;
        this.visited = new ArrayList<>();
        visited.addAll(visited);    // Add items, don't just point to the same Set
        visitedStack = new Stack<>();   // probably delete this
        this.allCaves = new HashMap<>();
    }

    public void findTheEnd() {
        // To find the end, start from the cave we're at and recursively search
        // all possible steps until we hit "end"
        // At each step, the "visitable" list is any of the current cave's
        // neighbors, EXCEPT for small caves we've already been to.

        // Are we at the end?
        if (current.getName().equals("end")) {
            // print it!
            System.out.println(this);
            // count it!
            pathsFound++;
            // go back!
            this.current = visitedStack.pop();
            return;   // how to handle the count?
        }

        // What are all the caves we can reach from here?
        List<Cave> visitableCaves = new ArrayList<>();
        // Start with all the neighbors
        visitableCaves.addAll(current.getAllNeighbors());
        // Remove small ones we've already been to
        visitableCaves =
                visitableCaves.stream()
                        .filter(cave -> (!cave.isSmall() || !visitedStack.contains(cave)))
                        .collect(Collectors.toList());
        for (Cave visitable : visitableCaves) {
            this.goTo(visitable);
            this.findTheEnd();
        }
        // When we get here, it's because we're at a dead end but NOT at the "end"
        // So just backtrack, making sure to remove things from the stack as we go
        if (!visitedStack.isEmpty()) {
            this.current = visitedStack.pop();
        }
        return;
    }

    public void goTo(Cave cave) {
        // Add the current cave to places we've "visited"
        this.addVisited(current);
        // Add the current cave to the "Visited" stack
        this.visitedStack.push(current);
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

    public Journey spawn(Cave nextVisit) {
        // Create a *new* journey just like this one, but move it to the 'nextVisit' cave
        Journey spawnedJourney = new Journey(current, visited);
        spawnedJourney.goTo(nextVisit);
        return spawnedJourney;
    }

    public Cave getCurrent() {
        return current;
    }

    public Integer getPathsFound() {
        return pathsFound;
    }

    @Override
    public String toString() {
        return visitedStack.stream().map(cave -> cave.getName()).collect(Collectors.joining(",")) +
                "," + current.getName();
//        return visited.stream().map(cave -> cave.getName())
//                .collect(Collectors.joining(", "))
//                + ", " + current.getName();
    }
}
