package org.hetzijzo.strucdocs.transpose;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hetzijzo.strucdocs.transpose.converter.ChordJsonDeserializer;
import org.hetzijzo.strucdocs.transpose.converter.ChordJsonSerializer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@JsonSerialize(using = ChordJsonSerializer.class)
@JsonDeserialize(using = ChordJsonDeserializer.class)
public class Chord {

    public static final String GROUNDNOTE_SEPERATOR = "/";

    private final Note note;
    private final Note groundNote;
    @Singular
    private Set<Interval> additions = new LinkedHashSet<>();

    public static Chord fromString(final String chordStringInput) {
        Note groundNote;
        String chordString = chordStringInput;
        if (StringUtils.contains(chordString, GROUNDNOTE_SEPERATOR)) {
            String groundNoteString = StringUtils.substringAfter(chordString, GROUNDNOTE_SEPERATOR);
            groundNote = getHighestMatching(Note.class, groundNoteString);
            chordString = StringUtils.substringBefore(chordString, GROUNDNOTE_SEPERATOR);
        } else {
            groundNote = null;
        }

        Note note = getHighestMatching(Note.class, chordString);
        if (note == null) {
            throw new InvalidChordException(chordStringInput);
        }

        ChordBuilder chordBuilder = Chord.builder()
            .note(note)
            .groundNote(groundNote);
        addAdditionsFromString(chordBuilder, StringUtils.substringAfter(chordString, note.notation));
        return chordBuilder.build();
    }

    private static ChordBuilder addAdditionsFromString(final ChordBuilder chordBuilder,
                                                       final String chordAdditionalStringInput) {
        String chordAdditionalString = chordAdditionalStringInput;
        while (StringUtils.isNotEmpty(chordAdditionalString)) {
            Interval addition = getHighestMatching(Interval.class, chordAdditionalString);
            if (addition != null) {
                chordBuilder.addition(addition);
                chordAdditionalString = StringUtils.substringAfter(chordAdditionalString, addition.notation);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNote().notation);
        getAdditions().forEach(addition -> sb.append(addition.notation));
        if (getGroundNote() != null) {
            sb.append(GROUNDNOTE_SEPERATOR);
            sb.append(getGroundNote());
        }
        return sb.toString();
    }
}
