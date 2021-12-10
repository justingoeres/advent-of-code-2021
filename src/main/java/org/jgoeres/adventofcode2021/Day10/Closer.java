package org.jgoeres.adventofcode2021.Day10;

import java.util.Arrays;
import java.util.Optional;

public enum Closer {
    CLOSE_PAREN(')', 3),
    CLOSE_SQUARE(']', 57),
    CLOSE_CURLY('}', 1197),
    CLOSE_ANGLE('>', 25137);

    private final Character character;
    private final Integer score;

    Closer(Character character, Integer score) {
        this.character = character;
        this.score = score;
    }

    public Character getCharacter() {
        return character;
    }

    public Integer getScore() {
        return score;
    }

    // Reverse lookup methods
    public static Optional<Closer> getCloserByChar(Character value) {
        return Arrays.stream(Closer.values())
                .filter(closer -> closer.character.equals(value))
                .findFirst();
    }

    public Boolean matches(Opener opener) {
        switch (opener) {
            case OPEN_PAREN:
                return (this.character.equals(CLOSE_PAREN.getCharacter()));
            case OPEN_SQUARE:
                return (this.character.equals(CLOSE_SQUARE.getCharacter()));
            case OPEN_CURLY:
                return (this.character.equals(CLOSE_CURLY.getCharacter()));
            case OPEN_ANGLE:
                return (this.character.equals(CLOSE_ANGLE.getCharacter()));
        }
        return false;
    }
}
