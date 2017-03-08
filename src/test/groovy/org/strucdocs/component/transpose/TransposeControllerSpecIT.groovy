package org.strucdocs.component.transpose

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.strucdocs.RestOAuthAbstractSpecification
import org.strucdocs.model.Chord
import org.strucdocs.model.Interval
import org.strucdocs.model.Note

import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.OK

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
class TransposeControllerSpecIT extends RestOAuthAbstractSpecification {

    def "should transpose with simple chord"() {
        given: "A chord"
            Chord chordRequest = Chord.builder().note(Note.C).build()
        when: "All artists are requested"
            ResponseEntity<Resource<TransposeResponse>> response = restTemplate.exchange(
                '/transpose', POST,
                new HttpEntity<>(TransposeRequest.builder().chord(chordRequest).key(-2).build(), headers),
                new ParameterizedTypeReference<Resource<TransposeResponse>>() {})
        then: "A status 200 should be returned"
            response.statusCode == OK
        then: "An empty list of arrays should be returned"
            TransposeResponse transposeResponse = response.body.content
            transposeResponse.getChord().toString() == "Bb"
    }

    def "should transpose with chord and additions"() {
        given: "A chord"
            Chord chordRequest = Chord.builder()
                .note(Note.BFlat)
                .addition(Interval.minor)
                .addition(Interval.seventh)
                .addition(Interval.sus2)
                .build()
        when: "All artists are requested"
            ResponseEntity<Resource<TransposeResponse>> response = restTemplate.exchange(
                '/transpose', POST,
                new HttpEntity<>(TransposeRequest.builder().chord(chordRequest).key(-2).build(), headers),
                new ParameterizedTypeReference<Resource<TransposeResponse>>() {})
        then: "A status 200 should be returned"
            response.statusCode == OK
        then: "An empty list of arrays should be returned"
            TransposeResponse transposeResponse = response.body.content
            transposeResponse.getChord().toString() == "Abm7sus2"
    }


    def "should transpose flat chord up"() {
        given: "A chord"
            Chord chordRequest = Chord.builder()
                .note(Note.BFlat)
                .groundNote(Note.D)
                .build()
        when: "All artists are requested"
            ResponseEntity<Resource<TransposeResponse>> response = restTemplate.exchange(
                '/transpose', POST,
                new HttpEntity<>(TransposeRequest.builder().chord(chordRequest).key(+2).build(), headers),
                new ParameterizedTypeReference<Resource<TransposeResponse>>() {})
        then: "A status 200 should be returned"
            response.statusCode == OK
        then: "An empty list of arrays should be returned"
            TransposeResponse transposeResponse = response.body.content
            transposeResponse.getChord().toString() == "C/E"
    }

    def "should transpose sharp chord down"() {
        given: "A chord"
            Chord chordRequest = Chord.builder()
                .note(Note.FSharp)
                .groundNote(Note.CSharp)
                .build()
        when: "All artists are requested"
            ResponseEntity<Resource<TransposeResponse>> response = restTemplate.exchange(
                '/transpose', POST,
                new HttpEntity<>(TransposeRequest.builder().chord(chordRequest).key(-3).build(), headers),
                new ParameterizedTypeReference<Resource<TransposeResponse>>() {})
        then: "A status 200 should be returned"
            response.statusCode == OK
        then: "An empty list of arrays should be returned"
            TransposeResponse transposeResponse = response.body.content
            transposeResponse.getChord().toString() == "D#/A#"
    }
}
