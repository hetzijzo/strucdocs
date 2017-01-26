package org.strucdocs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import org.strucdocs.model.converter.ChordJsonDeserializer;
import org.strucdocs.model.converter.ChordJsonSerializer;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

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
