package org.hetzijzo.strucdocs.document;

import lombok.Data;

import java.io.File;

@Data
public class StoredFile {

    private final File file;
    private final StoredFileDocument storedFileDocument;

}
