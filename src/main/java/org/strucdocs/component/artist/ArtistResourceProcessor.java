package org.strucdocs.component.artist;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.strucdocs.model.Artist;

@Component
@RequiredArgsConstructor
public class ArtistResourceProcessor implements ResourceProcessor<Resource<Artist>> {

    @Override
    public Resource<Artist> process(Resource<Artist> resource) {
        return resource;
    }
}
