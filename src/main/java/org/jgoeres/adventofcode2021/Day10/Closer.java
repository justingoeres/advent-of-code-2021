package org.jgoeres.adventofcode2021.Day10;

import java.util.Arrays;
import java.util.Optional;

public enum Closer {
    CLOSE_PAREN(')', 3L, 1L),
    CLOSE_SQUARE(']', 57L, 2L),
    CLOSE_CURLY('}', 1197L, 3L),
    CLOSE_ANGLE('>', 25137L, 4L);

    private final Character character;
    private final Long score;
    private final Long autocompleteScore;

    Closer(Character character, Long score, Long autocompleteScore) {
        this.character = character;
        this.score = score;
        this.autocompleteScore = autocompleteScore;
    }

    public Character getCharacter() {
        return character;
    }

    public Long getScore() {
        return score;
    }

    public Long getAutocompleteScore() {
        return autocompleteScore;
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
