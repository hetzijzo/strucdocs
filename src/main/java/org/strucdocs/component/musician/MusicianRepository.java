package org.strucdocs.component.musician;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.strucdocs.model.Musician;

import java.util.UUID;

@RepositoryRestResource(path = "musicians", collectionResourceRel = "musicians")
public interface MusicianRepository extends PagingAndSortingRepository<Musician, UUID> {
}
