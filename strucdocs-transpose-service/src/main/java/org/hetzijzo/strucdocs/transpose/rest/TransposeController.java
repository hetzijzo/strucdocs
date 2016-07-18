package org.hetzijzo.strucdocs.transpose.rest;

import org.hetzijzo.strucdocs.transpose.domain.Chord;
import org.hetzijzo.strucdocs.transpose.service.TransposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TransposeController {

    private final TransposeService transposeService;

    @Autowired
    public TransposeController(TransposeService transposeService) {
        this.transposeService = transposeService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public TransposeResponse transposeChord(@RequestBody @Validated TransposeRequest transposeRequest) {
        Chord transposedChord = transposeService.transposeChord(transposeRequest.getChord(), transposeRequest.getKey());

        return TransposeResponse.builder()
            .chord(transposedChord)
            .originalChord(transposeRequest.getChord())
            .key(transposeRequest.getKey())
            .build();
    }
}
