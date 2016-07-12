package org.hetzijzo.strucdocs.transpose;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TransposeController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Chord transposeChord(@RequestBody Chord chord, @RequestParam int key) {
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
