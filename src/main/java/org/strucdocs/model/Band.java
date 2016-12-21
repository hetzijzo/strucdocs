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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Band implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @NotNull
    public String name;

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL)
    public Set<Musician> musicians = new HashSet<>();

    @Builder
    @JsonCreator
    private Band(@JsonProperty("uuid") UUID uuid,
                 @JsonProperty("name") String name,
                 @JsonProperty("musicians") @Singular Set<Musician> musicians) {
        this.uuid = uuid;
        this.name = name;
        if (musicians != null) {
            this.musicians.addAll(musicians);
        }
    }

    public Set<Musician> getMusicians() {
        return Collections.unmodifiableSet(musicians);
    }
}
