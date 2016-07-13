package org.hetzijzo.strucdocs.transpose.domain;

import java.util.Arrays;
import java.util.LinkedList;

public enum Scale {

    UP(Note.A, Note.ASharp, Note.B, Note.C, Note.CSharp, Note.D, Note.DSharp,
        Note.E, Note.F, Note.FSharp, Note.G, Note.GSharp),
    DOWN(Note.AFlat, Note.G, Note.GFlat, Note.F, Note.E, Note.EFlat, Note.D, Note.DFlat,
        Note.C, Note.B, Note.BFlat, Note.A);

    final LinkedList<Note> notes;

    Scale(Note... notes) {
        this.notes = new LinkedList<>(Arrays.asList(notes));
    }

    public Note transpose(Note note, int steps) {
        int index = notes.indexOf(note);
        int newPosition = (index + steps) % 12;
        if (newPosition < 0) {
            newPosition += 12;
        }

        return notes.get(newPosition);
    }

    public int getSteps(Note originalNote, Note transposedNote) {
        return notes.indexOf(transposedNote) - notes.indexOf(originalNote);
    }

    public static Scale getScale(int steps) {
        return steps > 0 ? Scale.UP : Scale.DOWN;
    }
}
