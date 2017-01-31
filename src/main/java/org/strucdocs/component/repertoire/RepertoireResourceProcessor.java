package org.strucdocs.component.repertoire;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.strucdocs.model.Document;
import org.strucdocs.model.RepertoireSong;
import org.strucdocs.model.Song;

@Component
@RequiredArgsConstructor
public class RepertoireResourceProcessor implements ResourceProcessor<Resource<RepertoireSong>> {

    private final EntityLinks entityLinks;

    @Override
    public Resource<RepertoireSong> process(Resource<RepertoireSong> resource) {
        if (resource.getContent().getSongUuid() != null) {
            resource.add(
                entityLinks.linkFor(Song.class)
                    .slash(resource.getContent().getSongUuid())
                    .withRel("song")
            );
        }
        if (resource.getContent().getDocumentUuid() != null) {
            resource.add(
                entityLinks.linkFor(Document.class)
                    .slash(resource.getContent().getDocumentUuid())
                    .withRel("document")
            );
        }
        return resource;
    }
}
