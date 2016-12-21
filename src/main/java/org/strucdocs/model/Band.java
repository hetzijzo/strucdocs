package org.strucdocs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class Band implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @NotNull
    public String name;

    @Builder
    @JsonCreator
    private Band(@JsonProperty("uuid") UUID uuid,
                 @JsonProperty("name") String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
