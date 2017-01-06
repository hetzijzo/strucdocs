package org.strucdocs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public final class Document implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @Column(unique = true)
    private String sourceFile;
    private String filename;
    private String directory;

    private Long size;
    private Long pages;
    private String mimeType;

    private Instant modifyDate;
    private Instant accessDate;
    private Instant createDate;

    @Builder
    @JsonCreator
    private Document(@JsonProperty("uuid") UUID uuid,
                     @JsonProperty("sourceFile") String sourceFile,
                     @JsonProperty("filename") String filename,
                     @JsonProperty("directory") String directory,
                     @JsonProperty("size") Long size,
                     @JsonProperty("pages") Long pages,
                     @JsonProperty("mimeType") String mimeType,
                     @JsonProperty("modifyDate") Instant modifyDate,
                     @JsonProperty("accessDate") Instant accessDate,
                     @JsonProperty("createDate") Instant createDate) {
        this.uuid = uuid;
        this.sourceFile = sourceFile;
        this.filename = filename;
        this.directory = directory;
        this.size = size;
        this.pages = pages;
        this.mimeType = mimeType;
        this.modifyDate = modifyDate;
        this.accessDate = accessDate;
        this.createDate = createDate;
    }
}
