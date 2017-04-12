package org.strucdocs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class SongLine implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    private String lyrics;

    @ElementCollection
    private final Set<Chord> chords = new LinkedHashSet<>();

    @Builder
    @JsonCreator
    private SongLine(@JsonProperty("uuid") UUID uuid,
                     @JsonProperty("lyrics") String lyrics,
                     @JsonProperty("chords") @Singular Set<Chord> chords) {
        this.uuid = uuid;
        this.lyrics = lyrics;
        setChords(chords);
    }

    public void setChords(Set<Chord> chords) {
        if (chords != null) {
            this.chords.addAll(chords);
        }
    }

    public Set<Chord> getChords() {
        return Collections.unmodifiableSet(chords);
    }
}
