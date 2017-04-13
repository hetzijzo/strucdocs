package org.strucdocs.component.band

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.strucdocs.RestOAuthAbstractSpecification
import org.strucdocs.model.Band

import javax.transaction.Transactional

import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@ContextConfiguration
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
class BandRepositorySpecIT extends RestOAuthAbstractSpecification {

    def "should get empty band"() {
        given: "No there are no bands"
        when: "All bands are requested"
            ResponseEntity<Resources<Resource<Band>>> response = restTemplate.exchange(
                '/bands', GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Resources<Resource<Band>>>() {})
        then: "A status 200 should be returned"
            response.statusCode == OK
        then: "An empty list of arrays should be returned"
            response.body.content.size() == 0
    }

    def "should save, update and delete band"() {
        given: "An valid bands"
            Band band = Band.builder().name('U3').build()
        when: "This band is save"
            ResponseEntity<Resource<Band>> response = restTemplate.exchange(
                '/bands', POST, new HttpEntity(band, headers),
                new ParameterizedTypeReference<Resource<Band>>() {})
        then: "A status 201 should be returned"
            response.statusCode == CREATED

        when: "All bands are requested"
            ResponseEntity<Resources<Resource<Band>>> responseList = restTemplate.exchange(
                '/bands', GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Resources<Resource<Band>>>() {})
        then: "The new band should be included"
            Collection<Resource<Band>> bands = responseList.getBody().getContent()
            bands.size() == 1

        when: "The new band link is used"
            Resource<Band> bandResource = bands.iterator().next()
            band = bandResource.content

            ResponseEntity<Resource<Band>> bandResponse = restTemplate.exchange(
                bandResource.getLink("self").href, GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Resource<Band>>() {})

        then: "The new band should have the correct content"
            with(bandResponse.body.content) {
                [name] == ['U3']
                [uuid] != null
            }

        when: "The band is updated"
            band.name = 'U4'
            bandResponse = restTemplate.exchange(
                bandResource.getLink("self").href, PUT, new HttpEntity(band, headers),
                new ParameterizedTypeReference<Resource<Band>>() {})
        then: "The updated band should be returned"
            with(bandResponse.body.content) {
                [name] == ['U4']
            }
    }
}
