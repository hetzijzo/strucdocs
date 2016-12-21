package org.strucdocs.component.security;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.strucdocs.model.Musician;
import org.strucdocs.model.User;

@Component
@RequiredArgsConstructor
public class UserResourceProcessor implements ResourceProcessor<Resource<User>> {

    private final EntityLinks entityLinks;

    @Override
    public Resource<User> process(Resource<User> resource) {
        resource.add(
            entityLinks.linkToSingleResource(Musician.class, resource.getContent().getMusician().getUuid())
                .withRel("musician")
        );
        return resource;
    }
}
