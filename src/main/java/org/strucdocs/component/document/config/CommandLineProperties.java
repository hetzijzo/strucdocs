package org.strucdocs.component.document.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandLineProperties {
    private final String exifCommand;
    private final String extractTextCommand;
}
