package org.hetzijzo.strucdocs.song;

import org.hetzijzo.strucdocs.song.domain.Artist;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

//@RepositoryRestResource(collectionResourceRel = "artists", path = "artists")
public interface ArtistRepository extends PagingAndSortingRepository<Artist, UUID> {

    Artist findByNameContaining(String name);
}
