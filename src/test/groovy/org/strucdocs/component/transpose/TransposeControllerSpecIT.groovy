package org.strucdocs.component.transpose

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.strucdocs.RestOAuthAbstractSpecification
import org.strucdocs.model.Chord
import org.strucdocs.model.Note

import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.OK

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransposeControllerSpecIT extends RestOAuthAbstractSpecification {

    def "should transpose with simple chord"() {
        given: "A chord"
            Chord chordRequest = Chord.builder().note(Note.C).build()
        when: "All artists are requested"
            ResponseEntity<Resource<TransposeResponse>> response = restTemplate.exchange(
                '/transpose', POST,
                new HttpEntity<>(TransposeRequest.builder().chord(chordRequest).key(-1).build()),
                new ParameterizedTypeReference<Resource<TransposeResponse>>() {})
        then: "A status 200 should be returned"
            response.statusCode == OK
        then: "An empty list of arrays should be returned"
            TransposeResponse transposeResponse = response.body.content
            transposeResponse.getChord().toString() == "B"
    }
}
