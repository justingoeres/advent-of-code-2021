package org.jgoeres.adventofcode2021.Day04;

import org.jgoeres.adventofcode.common.XYPoint;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BingoCard {
    final Map<XYPoint, Integer> xyToValue = new HashMap<>();
    final Map<Integer, XYPoint> valueToXy = new HashMap<>();
    final Set<Integer> values = new HashSet<>();
    final Set<XYPoint> xyMarked = new HashSet<>();
    Integer size;

    public void addSpot(XYPoint spot, Integer value) {
        // Add a new spot to the card; map it both ways
        xyToValue.put(spot, value);
        valueToXy.put(value, spot);
        values.add(value);
    }

    public Boolean hasValue(Integer value) {
        return values.contains(value);
    }

    public void setMarked(Integer value) {
        // Find the XY spot that has this value, and set it to marked
        xyMarked.add(valueToXy.get(value));
        // Then remove that value from the remaining unmarked values
        values.remove(value);
    }

    public Boolean isWinner() {
        // Check all the rows & columns to see if they have marks
        // rows
        row:
        for (int i = 0; i < size; i++) { // row
            for (int j = 0; j < size; j++) {    // column
                if (!xyMarked.contains(new XYPoint(i, j))) {
                    // If any point in the row is NOT marked,
                    // the row is not the winner, go to the next row
                    continue row;
                }
            }
            // If we get here, we have a winner!
            return true;
        }
        column:
        for (int i = 0; i < size; i++) { // row
            for (int j = 0; j < size; j++) {    // column
                if (!xyMarked.contains(new XYPoint(j, i))) {
                    // If any point in the row is NOT marked,
                    // the row is not the winner, go to the next row
                    continue column;
                }
            }
            // If we get here, we have a winner!
            return true;
        }
        // if we get all the way here, no winners :(
        return false;
    }

    public Integer calculateScore(Integer lastCalled) {
        /**
         * The score of the winning board can now be calculated.
         * Start by finding the sum of all unmarked numbers on that board;
         * in this case, the sum is 188. Then, multiply that sum by the number
         * that was just called when the board won, 24, to get the final score,
         * 188 * 24 = 4512.
         */

        Integer sumOfUnmarked = values.stream().reduce(0, Integer::sum);
        Integer score = sumOfUnmarked * lastCalled;
        return score;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void reset() {
        // To reset the card, move all the marked points
        // back to the unmarked 'values'
        // Unmark everything
        xyMarked.clear();
        values.addAll(valueToXy.keySet());
    }

    public void printCard() {
        // print the card!
        for (int i = 0; i < size; i++) { // row
            for (int j = 0; j < size; j++) {    // column
                final String MARK =
                        xyMarked.contains(new XYPoint(i, j)) ? "*" : " ";
                System.out.print(MARK + xyToValue.get(new XYPoint(i, j)) + MARK);
            }
            System.out.println(); // end the line
        }
        System.out.println(); // blank line
    }
}
