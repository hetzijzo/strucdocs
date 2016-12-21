package org.strucdocs.component.band;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.strucdocs.model.Band;
import org.strucdocs.model.Musician;
import org.strucdocs.model.Repertoire;

@Component
@RequiredArgsConstructor
public class BandResourceProcessor implements ResourceProcessor<Resource<Band>> {

    private final EntityLinks entityLinks;

    @Override
    public Resource<Band> process(Resource<Band> resource) {
        resource.add(
            entityLinks.linkFor(Musician.class)
                .slash("search")
                .slash(String.format("findByBandUuid?bandUuid=%s", resource.getContent().getUuid()))
                .withRel("musicians")
        );
        resource.add(
            entityLinks.linkFor(Repertoire.class)
                .slash("search")
                .slash(String.format("findByBandUuid?bandUuid=%s", resource.getContent().getUuid()))
                .withRel("repertoires")
        );
        return resource;
    }
}
