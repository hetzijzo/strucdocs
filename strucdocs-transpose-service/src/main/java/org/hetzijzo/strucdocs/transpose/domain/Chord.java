package org.hetzijzo.strucdocs.transpose.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import org.hetzijzo.strucdocs.transpose.converter.ChordJsonDeserializer;
import org.hetzijzo.strucdocs.transpose.converter.ChordJsonSerializer;

import java.util.LinkedHashSet;
import java.util.Set;

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
