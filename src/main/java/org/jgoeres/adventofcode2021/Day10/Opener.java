package org.jgoeres.adventofcode2021.Day10;

import java.util.Arrays;
import java.util.Optional;

public enum Opener {
    OPEN_PAREN('('),
    OPEN_SQUARE('['),
    OPEN_CURLY('{'),
    OPEN_ANGLE('<');

    private final Character character;

    Opener(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    // Reverse lookup methods
    public static Optional<Opener> getOpenerByChar(Character value) {
        return Arrays.stream(Opener.values())
                .filter(opener -> opener.character.equals(value))
                .findFirst();
    }
}
