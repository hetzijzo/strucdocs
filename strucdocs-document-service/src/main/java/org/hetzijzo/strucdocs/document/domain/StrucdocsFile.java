package org.hetzijzo.strucdocs.document.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class StrucdocsFile {

    @Id
    @JsonProperty("SourceFile")
    private String sourceFile;
    @JsonProperty("FileName")
    private String filename;
    @JsonProperty("Directory")
    private String directory;
    @JsonProperty("FileSize")
    private Long size;
    @JsonProperty("PageCount")
    private Long pages;
    //    @JsonProperty("FileModifyDate")
//    private Instant modifyDate;
//    @JsonProperty("FileAccessDate")
//    private Instant accessDate;
//    @JsonProperty("FileCreateDate")
//    private Instant createDate;
    @JsonProperty("MIMEType")
    private String mimeType;

}
