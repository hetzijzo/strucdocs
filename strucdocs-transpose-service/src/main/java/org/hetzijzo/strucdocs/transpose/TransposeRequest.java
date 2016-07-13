package org.hetzijzo.strucdocs.transpose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransposeRequest {

    @NotNull
    private Chord chord;
    private int key;
}
