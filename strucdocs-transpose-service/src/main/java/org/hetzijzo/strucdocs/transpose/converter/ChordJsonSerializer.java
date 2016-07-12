package org.hetzijzo.strucdocs.transpose.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.hetzijzo.strucdocs.transpose.Chord;

import java.io.IOException;

public class ChordJsonSerializer
    extends JsonSerializer<Chord> {

    @Override
    public void serialize(Chord value, JsonGenerator jsonGenerator, SerializerProvider serializers)
        throws IOException, JsonProcessingException {
        jsonGenerator.writeString(value.toString());
    }
}
