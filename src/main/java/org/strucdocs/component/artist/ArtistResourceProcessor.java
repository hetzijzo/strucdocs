package org.strucdocs.component.artist;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.strucdocs.model.Artist;
import org.strucdocs.model.Song;

@Component
@RequiredArgsConstructor
public class ArtistResourceProcessor implements ResourceProcessor<Resource<Artist>> {

    private final EntityLinks entityLinks;

    @Override
    public Resource<Artist> process(Resource<Artist> resource) {
        resource.add(
            entityLinks.linkFor(Song.class)
                .slash("search")
                .slash(String.format("findByArtistUuid?artistUuid=%s", resource.getContent().getUuid()))
                .withRel("songs")
        );
        return resource;
    }
}
