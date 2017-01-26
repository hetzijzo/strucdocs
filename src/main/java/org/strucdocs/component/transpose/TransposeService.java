package org.strucdocs.component.transpose;

import org.springframework.stereotype.Service;
import org.strucdocs.model.Chord;
import org.strucdocs.model.Note;
import org.strucdocs.model.Scale;

@Service
class TransposeService {

    public Chord transposeChord(Chord chord, int key) {
        Note note = chord.getNote();
        Scale scale = getScale(note, key);
        int steps = getSteps(note, key);

        Chord.ChordBuilder chordBuilder = Chord.builder();
        chordBuilder.note(chord.getNote().transpose(scale, steps));
        if (chord.getGroundNote() != null) {
            chordBuilder.groundNote(chord.getGroundNote().transpose(scale, steps));
        }
        chordBuilder.additions(chord.getAdditions());
        return chordBuilder.build();
    }

    private Scale getScale(Note note, int key) {
        return note.isFlat() ? Scale.DOWN : note.isSharp() ? Scale.UP : key > 0 ? Scale.UP : Scale.DOWN;
    }

    private int getSteps(Note note, int key) {
        int steps = key < 0 ? key * -1 : key;
        if ((note.isFlat() && key > 0) || (note.isSharp() && key < 0)) {
            steps = steps * -1;
        }
        return steps;
    }
}
