package org.strucdocs.component.transpose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.strucdocs.model.Chord;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransposeResponse {

    private Chord chord;
    private Chord originalChord;
    private int key;
}
