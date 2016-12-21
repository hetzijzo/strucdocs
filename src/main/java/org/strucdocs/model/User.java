package org.strucdocs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @NotNull
    private String username;

    @NotNull
    private String name;

    @OneToOne
    @JoinColumn(name = "musician_uuid")
    @Getter(onMethod = @__({@JsonIgnore}))
    private Musician musician;

    @Builder
    @JsonCreator
    private User(@JsonProperty("uuid") UUID uuid,
                 @JsonProperty("username") String username,
                 @JsonProperty("name") String name,
                 @JsonProperty("musician") Musician musician) {
        this.uuid = uuid;
        this.username = username;
        this.name = name;
        this.musician = musician;
    }
}
