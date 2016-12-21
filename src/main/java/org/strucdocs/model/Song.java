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
public final class Song implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @NotNull
    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_uuid")
    @NotNull
    private Artist artist;

    private String key;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "song_uuid")
    private final Set<SongPart> parts = new LinkedHashSet<>();

    @Builder
    @JsonCreator
    private Song(@JsonProperty("uuid") UUID uuid,
                 @JsonProperty("title") String title,
                 @JsonProperty("artist") Artist artist,
                 @JsonProperty("parts") @Singular Set<SongPart> parts) {
        this.uuid = uuid;
        this.title = title;
        this.artist = artist;
        setParts(parts);
    }

    public void setParts(Set<SongPart> parts) {
        if (parts != null) {
            this.parts.addAll(parts);
        }
    }

    public Set<SongPart> getParts() {
        return Collections.unmodifiableSet(parts);
    }
}
