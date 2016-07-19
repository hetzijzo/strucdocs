package org.hetzijzo.strucdocs.transpose.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hetzijzo.strucdocs.transpose.domain.Chord;

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