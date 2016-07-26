package org.hetzijzo.strucdocs.repertoire;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.client.Traverson;

import java.net.URI;

import static org.springframework.hateoas.client.Hop.rel;

public class RepertoireRepositoryTest {

    @Test
    public void test() throws Exception {
        Traverson traverson = new Traverson(URI.create("http://localhost:8082"), MediaTypes.HAL_JSON);

        ParameterizedTypeReference<Resource> resourceParameterizedTypeReference =
            new ParameterizedTypeReference<Resource>() {
            };

        Resource resource = traverson
            .follow(rel("songs"))
            .follow("$._embedded.songs[0]._links.self.href")
            .toObject(resourceParameterizedTypeReference);

        Traverson.TraversalBuilder follow = traverson.follow("songs", "self", "c43653ce-2d8b-49ef-ad90-1b1a3f143360");
        Link link = follow.asLink();
    }

}
