package org.jgoeres.adventofcode2021.Day10;

import static org.jgoeres.adventofcode2021.Day10.Closer.*;
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

    // Reverse lookup methods
    public static Optional<Opener> getOpenerByChar(Character value) {
        return Arrays.stream(Opener.values())
                .filter(opener -> opener.character.equals(value))
                .findFirst();
    }

    public Closer getMatch() {
        switch (this) {
            case OPEN_PAREN:
                return CLOSE_PAREN;
            case OPEN_SQUARE:
                return CLOSE_SQUARE;
            case OPEN_CURLY:
                return CLOSE_CURLY;
            case OPEN_ANGLE:
                return CLOSE_ANGLE;
        }
        return null;
    }
}
