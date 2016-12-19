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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SongPart implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @NotNull
    private String type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "songpart_uuid")
    private Set<SongLine> lines = new LinkedHashSet<>();

    @Builder
    @JsonCreator
    private SongPart(@JsonProperty("uuid") UUID uuid,
                     @JsonProperty("type") String type,
                     @JsonProperty("lines") @Singular Set<SongLine> lines) {
        this.uuid = uuid;
        this.type = type;
        if (lines != null) {
            this.lines.addAll(lines);
        }
    }

    public Set<SongLine> getLines() {
        return Collections.unmodifiableSet(lines);
    }
}
