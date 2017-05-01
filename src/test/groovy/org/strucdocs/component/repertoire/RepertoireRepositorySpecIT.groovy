package org.strucdocs.component.repertoire

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.strucdocs.RestOAuthAbstractSpecification
import org.strucdocs.model.Repertoire

import javax.transaction.Transactional

import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@ContextConfiguration
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
class RepertoireRepositorySpecIT extends RestOAuthAbstractSpecification {

    def "should get empty repertoire"() {
        given: "There is no repertoire"
        when: "All repertoires are requested"
            ResponseEntity<Resources<Resource<Repertoire>>> response = restTemplate.exchange(
                '/repertoires', GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Resources<Resource<Repertoire>>>() {})
        then: "A status 200 should be returned"
            response.statusCode == OK
        then: "An empty list of arrays should be returned"
            response.body.content.size() == 0
    }

    def "should save, update and delete repertoire"() {
        given: "A valid repertoire"
            Repertoire repertoire = Repertoire.builder().name('Repertoire1').build()
        when: "This repertoire is saved"
            ResponseEntity<Resource<Repertoire>> response = restTemplate.exchange(
                '/repertoires', POST, new HttpEntity(repertoire, headers),
                new ParameterizedTypeReference<Resource<Repertoire>>() {})
        then: "A status 201 should be returned"
            response.statusCode == CREATED

        when: "All repertoires are requested"
            ResponseEntity<Resources<Resource<Repertoire>>> responseList = restTemplate.exchange(
                '/repertoires', GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Resources<Resource<Repertoire>>>() {})
        then: "The new repertoire should be included"
            Collection<Resource<Repertoire>> repertoires = responseList.getBody().getContent()
            repertoires.size() == 1

        when: "The new repertoire link is used"
            Resource<Repertoire> repertoireResource = repertoires.iterator().next()
            repertoire = repertoireResource.content

            ResponseEntity<Resource<Repertoire>> repertoireResponse = restTemplate.exchange(
                repertoireResource.getLink("self").href, GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Resource<Repertoire>>() {})

        then: "The new repertoire should have the correct content"
            with(repertoireResponse.body.content) {
                [name] == ['Repertoire1']
                [uuid] != null
            }

        when: "The repertoire is updated"
            repertoire.name = 'U4'
            repertoireResponse = restTemplate.exchange(
                repertoireResource.getLink("self").href, PUT, new HttpEntity(repertoire, headers),
                new ParameterizedTypeReference<Resource<Repertoire>>() {})
        then: "The updated repertoire should be returned"
            with(repertoireResponse.body.content) {
                [name] == ['U4']
            }
    }
}
