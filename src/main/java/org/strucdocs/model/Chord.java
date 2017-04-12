package org.strucdocs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import org.apache.commons.lang3.StringUtils;
import org.strucdocs.model.converter.ChordJsonDeserializer;
import org.strucdocs.model.converter.ChordJsonSerializer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@JsonSerialize(using = ChordJsonSerializer.class)
@JsonDeserialize(using = ChordJsonDeserializer.class)
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
        if (additions != null) {
            this.additions.addAll(additions);
        }
    }

    public Set<Interval> getAdditions() {
        return Collections.unmodifiableSet(additions);
    }


    public static Chord fromString(String chordString) {
        Note groundNote;
        if (StringUtils.contains(chordString, GROUNDNOTE_SEPERATOR)) {
            String groundNoteString = StringUtils.substringAfter(chordString, "/");
            groundNote = getHighestMatching(Note.class, groundNoteString);
            chordString = StringUtils.substringBefore(chordString, "/");
        } else {
            groundNote = null;
        }

        Note note = getHighestMatching(Note.class, chordString);
        if (note == null) {
            return null;
        }

        Chord.ChordBuilder chordBuilder = Chord.builder()
            .note(note)
            .groundNote(groundNote);

        String chordAdditionalString = StringUtils.substringAfter(chordString, note.notation);
        while (!chordAdditionalString.isEmpty()) {
            Interval addition = getHighestMatching(Interval.class, chordAdditionalString);
            if (addition != null) {
                chordBuilder.addition(addition);
                chordAdditionalString = StringUtils.substringAfter(chordAdditionalString, addition.notation);
            }
        }

        return chordBuilder.build();
    }

    private static <T extends Enum> T getHighestMatching(Class<T> itemsClass, final String stringValue) {
        Optional<Map.Entry<T, Double>> max = getMatchingScores(itemsClass, stringValue)
            .entrySet()
            .stream()
            .filter(e -> stringValue.indexOf(e.getKey().toString()) == 0)
            .max(Comparator.comparing(Map.Entry::getValue));
        return max.map(Map.Entry::getKey).orElse(null);
    }

    private static <T extends Enum> Map<T, Double> getMatchingScores(Class<T> itemsClass, final String stringValue) {
        return Arrays.stream(itemsClass.getEnumConstants())
            .filter(item -> stringValue.contains(item.toString()))
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
