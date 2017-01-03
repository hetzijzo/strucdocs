package org.strucdocs.config.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * A specific ObjectMapper type with a YAMLFactory. This class gives the posibility to wire a specific ObjectMapper for
 * YAML by type.
 */
public class YamlObjectMapper extends ObjectMapper {

    public YamlObjectMapper() {
        super(new YAMLFactory());
    }
}
