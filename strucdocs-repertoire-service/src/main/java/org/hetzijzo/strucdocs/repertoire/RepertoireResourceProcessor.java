package org.hetzijzo.strucdocs.repertoire;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.hetzijzo.strucdocs.repertoire.domain.RepertoireSong;
import org.springframework.cloud.client.hypermedia.DiscoveredResource;
import org.springframework.data.rest.webmvc.support.BaseUriLinkBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RepertoireResourceProcessor implements ResourceProcessor<Resource<RepertoireSong>> {

    @NonNull
    private final DiscoveredResource songResource;

    @Override
    public Resource<RepertoireSong> process(Resource<RepertoireSong> resource) {
        if (resource.getContent().getSongUuid() != null) {
            resource.add(
                BaseUriLinkBuilder.create(URI.create(songResource.getLink().expand().getHref()))
                    .slash(resource.getContent().getSongUuid())
                    .withRel("song")
            );
        }
        return resource;
    }
}
