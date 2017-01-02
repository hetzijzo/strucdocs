package org.strucdocs.component.artist

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.strucdocs.RestOAuthAbstractSpecification
import org.strucdocs.model.Artist

import javax.transaction.Transactional

import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@ContextConfiguration
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtistRepositorySpecIT extends RestOAuthAbstractSpecification {

    @Rollback
    def "should get empty artists"() {
        given: "No there are no artists"
        when: "All artists are requested"
            ResponseEntity<Resources<Resource<Artist>>> response = restTemplate.exchange(
                '/artists', GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Resources<Resource<Artist>>>() {})
        then: "A status 200 should be returned"
            response.statusCode == OK
        then: "An empty list of arrays should be returned"
            response.body.content.size() == 0
    }

    def "should save, update and delete artist"() {
        given: "An valid artists"
            Artist artist = Artist.builder().name("Tina Turner").build()
        when: "This artist is save"
            ResponseEntity<Resource<Artist>> response = restTemplate.exchange(
                '/artists', POST, new HttpEntity(artist, headers),
                new ParameterizedTypeReference<Resource<Artist>>() {})
        then: "A status 201 should be returned"
            response.statusCode == CREATED

        when: "All artists are requested"
            ResponseEntity<Resources<Resource<Artist>>> responseList = restTemplate.exchange(
                '/artists', GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Resources<Resource<Artist>>>() {})
        then: "The new artist should be included"
            Collection<Resource<Artist>> artists = responseList.getBody().getContent()
            artists.size() == 1

        when: "The new artist link is used"
            Resource<Artist> artistResource = artists.iterator().next()
            artist = artistResource.content

            ResponseEntity<Resource<Artist>> artistResponse = restTemplate.exchange(
                artistResource.getLink("self").href, GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Resource<Artist>>() {})

        then: "The new artist should have the correct content"
            with(artistResponse.body.content) {
                [name] == ['Tina Turner']
                [uuid] != null
            }

        when: "The artist is updated"
            artist.name = 'Tina Turner2'
            artistResponse = restTemplate.exchange(
                artistResource.getLink("self").href, PUT, new HttpEntity(artist, headers),
                new ParameterizedTypeReference<Resource<Artist>>() {})
        then: "The updated artist should be returned"
            with(artistResponse.body.content) {
                [name] == ['Tina Turner2']
            }
    }
}
