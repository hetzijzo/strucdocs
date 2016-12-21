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
public final class RepertoireSong implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @NotNull
    private UUID songUuid;

    private UUID documentUuid;

    private String key;

    @Builder
    @JsonCreator
    private RepertoireSong(@JsonProperty("uuid") UUID uuid,
                           @JsonProperty("songUuid") UUID songUuid,
                           @JsonProperty("documentUuid") UUID documentUuid,
                           @JsonProperty("key") String key) {
        this.uuid = uuid;
        this.songUuid = songUuid;
        this.documentUuid = documentUuid;
        this.key = key;
    }
}
