package org.strucdocs.component.artist;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.strucdocs.model.Artist;

import java.util.UUID;

@RepositoryRestResource(path = "artists", collectionResourceRel = "artists")
public interface ArtistRepository extends PagingAndSortingRepository<Artist, UUID> {

    Artist findByNameContaining(String name);
}
