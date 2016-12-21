package org.strucdocs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class Musician implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @NotNull
    private String name;

    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "band_uuid")
    private Band band;

    @ElementCollection(targetClass = Instrument.class)
    @CollectionTable(name = "instrument", joinColumns = @JoinColumn(name = "musician_uuid"))
    @Column(name = "instruments", nullable = false)
    @Enumerated(EnumType.STRING)
    private final Set<Instrument> instruments = new HashSet<>();

    @Builder
    @JsonCreator
    private Musician(@JsonProperty("uuid") UUID uuid,
                     @JsonProperty("name") String name,
                     @JsonProperty("band") Band band,
                     @JsonProperty("instruments") @Singular Set<Instrument> instruments) {
        this.uuid = uuid;
        this.name = name;
        this.band = band;
        setInstruments(instruments);
    }

    public void setInstruments(Set<Instrument> instruments) {
        if (instruments != null) {
            this.instruments.addAll(instruments);
        }
    }

    public Set<Instrument> getInstruments() {
        return Collections.unmodifiableSet(instruments);
    }
}
