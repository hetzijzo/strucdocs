package org.strucdocs.model.converter;

import org.strucdocs.model.Chord;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ChordConverter
    implements AttributeConverter<Chord, String> {

    @Override
    public String convertToDatabaseColumn(Chord chord) {
        return chord.toString();
    }

    @Override
    public Chord convertToEntityAttribute(String dbData) {
        return Chord.fromString(dbData);
    }
}
