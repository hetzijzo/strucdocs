package org.hetzijzo.strucdocs.transpose.service;

import org.hetzijzo.strucdocs.transpose.domain.Chord;
import org.hetzijzo.strucdocs.transpose.domain.Scale;
import org.springframework.stereotype.Service;

@Service
public class TransposeService {

    public Chord transposeChord(Chord chord, int key) {
        Scale scale = getScale(key);
        int steps = getSteps(key);

        Chord.ChordBuilder chordBuilder = Chord.builder();
        chordBuilder.note(chord.getNote().transpose(scale, steps));
        if (chord.getGroundNote() != null) {
            chordBuilder.groundNote(chord.getGroundNote().transpose(scale, steps));
        }
        chordBuilder.additions(chord.getAdditions());
        return chordBuilder.build();
    }

    private int getSteps(int key) {
        return key < 0 ? key * -1 : key;
    }

    private Scale getScale(int key) {
        return key > 0 ? Scale.UP : Scale.DOWN;
    }
}
