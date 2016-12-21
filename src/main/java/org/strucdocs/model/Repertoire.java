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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Repertoire implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "band_uuid")
    private Band band;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "repertoire_uuid")
    private final Set<RepertoireSong> songs = new LinkedHashSet<>();

    @Builder
    @JsonCreator
    private Repertoire(@JsonProperty("uuid") UUID uuid,
                       @JsonProperty("band") Band band,
                       @JsonProperty("name") String name,
                       @JsonProperty("songs") @Singular Set<RepertoireSong> songs) {
        this.uuid = uuid;
        this.band = band;
        this.name = name;
        setSongs(songs);
    }

    public void setSongs(Set<RepertoireSong> songs) {
        if (songs != null) {
            this.songs.addAll(songs);
        }
    }

    public Set<RepertoireSong> getSongs() {
        return Collections.unmodifiableSet(songs);
    }
}
