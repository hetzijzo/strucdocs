package org.strucdocs.component.transpose;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.strucdocs.model.Chord;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TransposeController {

    private final TransposeService transposeService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<TransposeResponse>> transposeChord(@RequestBody @Validated
                                                                          TransposeRequest transposeRequest) {
        Chord transposedChord = transposeService.transposeChord(transposeRequest.getChord(), transposeRequest.getKey());

        return ResponseEntity.ok(
            new Resource<>(TransposeResponse.builder()
                .chord(transposedChord)
                .originalChord(transposeRequest.getChord())
                .key(transposeRequest.getKey())
                .build())
        );
    }
}
