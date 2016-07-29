package org.hetzijzo.strucdocs.document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandLineConfiguration {

    private final String exifCommand;
    private final String extractTextCommand;
}
