package org.strucdocs.component.repertoire;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.strucdocs.component.song.SongRepository;
import org.strucdocs.model.RepertoireSong;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class RepertoireResourceProcessor implements ResourceProcessor<Resource<RepertoireSong>> {

    @Override
    public Resource<RepertoireSong> process(Resource<RepertoireSong> resource) {
        if (resource.getContent().getSongUuid() != null) {
            resource.add(
                linkTo(SongRepository.class)
                    .slash(resource.getContent().getSongUuid())
                    .withRel("song")
            );
        }
        return resource;
    }
}
