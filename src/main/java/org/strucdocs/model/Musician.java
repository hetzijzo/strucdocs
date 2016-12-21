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
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Musician implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @ManyToOne
    @JoinColumn(name = "band_uuid")
    @NotNull
    private Band band;

//    private Set<Instrument> instruments = new HashSet<>();

    @Builder
    @JsonCreator
    private Musician(@JsonProperty("uuid") UUID uuid,
                     @JsonProperty("name") String name,
                     @JsonProperty("band") Band band,
                     @JsonProperty("instruments") @Singular Set<Instrument> instruments) {
        this.uuid = uuid;
        this.name = name;
        this.band = band;
//        if (instruments != null) {
//            this.instruments.addAll(instruments);
//        }
    }

//    public Set<Instrument> getInstruments() {
//        return Collections.unmodifiableSet(instruments);
//    }
}
