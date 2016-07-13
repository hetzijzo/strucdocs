package org.hetzijzo.strucdocs.transpose.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hetzijzo.strucdocs.transpose.InvalidChordException;
import org.hetzijzo.strucdocs.transpose.domain.Chord;
import org.hetzijzo.strucdocs.transpose.domain.Interval;
import org.hetzijzo.strucdocs.transpose.domain.Note;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChordJsonDeserializer
    extends JsonDeserializer<Chord> {

    @Override
    public Chord deserialize(JsonParser jsonParser, DeserializationContext context)
        throws IOException {
        return getChordFromString(jsonParser.getValueAsString());
    }

    private Chord getChordFromString(final String chordStringInput) {
        Note groundNote;
        String chordString = chordStringInput;
        if (StringUtils.contains(chordString, Chord.GROUNDNOTE_SEPERATOR)) {
            String groundNoteString = StringUtils.substringAfter(chordString, Chord.GROUNDNOTE_SEPERATOR);
            groundNote = getHighestMatching(Note.class, groundNoteString);
            chordString = StringUtils.substringBefore(chordString, Chord.GROUNDNOTE_SEPERATOR);
        } else {
            groundNote = null;
        }

        Note note = getHighestMatching(Note.class, chordString);
        if (note == null) {
            throw new InvalidChordException(chordStringInput);
        }

        Chord.ChordBuilder chordBuilder = Chord.builder()
            .note(note)
            .groundNote(groundNote);
        addAdditionsFromString(chordBuilder, StringUtils.substringAfter(chordString, note.getNotation()));
        return chordBuilder.build();
    }

    private static Chord.ChordBuilder addAdditionsFromString(final Chord.ChordBuilder chordBuilder,
                                                             final String chordAdditionalStringInput) {
        String chordAdditionalString = chordAdditionalStringInput;
        while (StringUtils.isNotEmpty(chordAdditionalString)) {
            Interval addition = getHighestMatching(Interval.class, chordAdditionalString);
            if (addition != null) {
                chordBuilder.addition(addition);
                chordAdditionalString = StringUtils.substringAfter(chordAdditionalString, addition.getNotation());
            }
        }
        return chordBuilder;
    }

    private static <T extends Enum> T getHighestMatching(final Class<T> itemsClass,
                                                         final String stringValue) {
        Optional<T> max = getMatchingScores(itemsClass, stringValue)
            .entrySet().stream()
            .filter(e -> StringUtils.indexOf(stringValue, e.getKey().toString()) == 0)
            .max((e1, e2) -> ObjectUtils.compare(e1.getValue(), e2.getValue()))
            .map(Map.Entry::getKey);
        return max.orElse(null);
    }

    private static <T extends Enum> Map<T, Double> getMatchingScores(final Class<T> itemsClass,
                                                                     final String stringValue) {
        return Arrays.stream(itemsClass.getEnumConstants())
            .filter(item -> StringUtils.contains(stringValue, item.toString()))
            .collect(Collectors.toMap(
                item -> item,
                item -> StringUtils.getJaroWinklerDistance(item.toString(), stringValue)
            ));
    }
}
