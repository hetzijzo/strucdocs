package org.hetzijzo.strucdocs.transpose.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.hetzijzo.strucdocs.transpose.Chord;

import java.io.IOException;

public class ChordJsonDeserializer
    extends JsonDeserializer<Chord> {

    @Override
    public Chord deserialize(JsonParser jsonParser, DeserializationContext context)
        throws IOException {
        return Chord.fromString(jsonParser.getValueAsString());
    }
}
