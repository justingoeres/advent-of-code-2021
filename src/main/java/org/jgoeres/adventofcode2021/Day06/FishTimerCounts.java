package org.jgoeres.adventofcode2021.Day06;

import java.util.HashMap;
import java.util.Map;

public class FishTimerCounts {
    private final Map<Integer, Long> map = new HashMap<>();
    private final Integer GENERATION_LENGTH;

    public FishTimerCounts(Integer GENERATION_LENGTH) {
        this.GENERATION_LENGTH = GENERATION_LENGTH;
    }

    public void putTimerCount(Integer timer, Long count) {
        // For a timer value of 6 (the generation length),
        // a PUT needs to check to see if the target timer value
        // already exists, and if so, add to it.
        if (timer == GENERATION_LENGTH && map.containsKey(timer)) {
            // if the vent already exists, increment it
            map.compute(timer, (key, value) ->
                    value + count);
        } else {
            // put normally
            map.put(timer, count);
        }
    }

    public Map<Integer, Long> getMap() {
        return map;
    }
}
