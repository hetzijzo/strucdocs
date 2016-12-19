package org.strucdocs.model.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.strucdocs.model.Chord;

import java.io.IOException;

public class ChordJsonSerializer
    extends JsonSerializer<Chord> {

    @Override
    public void serialize(Chord value, JsonGenerator jsonGenerator, SerializerProvider serializers)
        throws IOException {
        jsonGenerator.writeString(value.toString());
    }
}
