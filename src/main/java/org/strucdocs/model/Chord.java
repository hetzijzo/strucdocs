package org.strucdocs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.strucdocs.exception.InvalidChordException;
import org.strucdocs.model.converter.ChordJsonDeserializer;
import org.strucdocs.model.converter.ChordJsonSerializer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@JsonSerialize(using = ChordJsonSerializer.class)
@JsonDeserialize(using = ChordJsonDeserializer.class)
@Slf4j
public final class Chord implements Serializable {

    public static final String GROUNDNOTE_SEPERATOR = "/";

    private final Note note;
    private final Note groundNote;
    private final Set<Interval> additions = new LinkedHashSet<>();

    @Builder
    @JsonCreator
    private Chord(@JsonProperty("note") Note note,
                  @JsonProperty("groundNote") Note groundNote,
                  @JsonProperty("additions") @Singular Set<Interval> additions) {
        this.note = note;
        this.groundNote = groundNote;
        this.additions.addAll(
            Optional.ofNullable(additions)
                .orElse(Collections.emptySet())
        );
    }

    public Set<Interval> getAdditions() {
        return Collections.unmodifiableSet(additions);
    }

    public static boolean isChord(final String chordStringInput) {
        try {
            fromString(chordStringInput);
            return true;
        } catch (InvalidChordException ex) {
            return false;
        }
    }

    public static Chord fromString(final String chordStringInput) {
        Note groundNote;
        String chordString = chordStringInput;
        if (StringUtils.contains(chordString, Chord.GROUNDNOTE_SEPERATOR)) {
            String groundNoteString = StringUtils.substringAfter(chordString, Chord.GROUNDNOTE_SEPERATOR);
            groundNote = getHighestMatching(Note.class, groundNoteString).orElse(null);
            chordString = StringUtils.substringBefore(chordString, Chord.GROUNDNOTE_SEPERATOR);
        } else {
            groundNote = null;
        }

        Note note = getHighestMatching(Note.class, chordString)
            .orElseThrow(() -> new InvalidChordException(chordStringInput));

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
            Optional<Interval> optionalAddition = getHighestMatching(Interval.class, chordAdditionalString);
            if (optionalAddition.isPresent()) {
                Interval addition = optionalAddition.get();
                chordBuilder.addition(addition);
                chordAdditionalString = StringUtils.substringAfter(chordAdditionalString, addition.getNotation());
            }
        }
        return chordBuilder;
    }

    private static <T extends Enum> Optional<T> getHighestMatching(final Class<T> itemsClass,
                                                                   final String stringValue) {
        Optional<T> optionalMax = getMatchingScores(itemsClass, stringValue)
            .entrySet().stream()
            .filter(e -> StringUtils.indexOf(stringValue, e.getKey().toString()) == 0)
            .max((e1, e2) -> ObjectUtils.compare(e1.getValue(), e2.getValue()))
            .map(Map.Entry::getKey);
        return optionalMax;
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
