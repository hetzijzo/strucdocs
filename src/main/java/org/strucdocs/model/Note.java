package org.strucdocs.model;

public enum Note {

    A("A"),
    ASharp("A#"),
    BFlat("Bb"),
    B("B"),
    C("C"),
    CSharp("C#"),
    DFlat("Db"),
    D("D"),
    DSharp("D#"),
    EFlat("Eb"),
    E("E"),
    F("F"),
    FSharp("F#"),
    GFlat("Gb"),
    G("G"),
    GSharp("G#"),
    AFlat("Ab");

    String notation;

    Note(String notation) {
        this.notation = notation;
    }

    public boolean isFlat() {
        return getNotation().endsWith("b");
    }

    public boolean isSharp() {
        return getNotation().endsWith("#");
    }

    /**
     * Gets the String notation of this Note.
     *
     * @return String notation
     */
    public String getNotation() {
        return notation;
    }

    /**
     * Transpose on the given Scale with the given amount of steps.
     *
     * @param scale The Scale to transpose on.
     * @param steps A positive number of steps.
     * @return The transposed note.
     */
    public Note transpose(Scale scale, int steps) {
        return scale.transpose(this, steps);
    }

    @Override
    public String toString() {
        return notation;
    }
}
