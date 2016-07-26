package org.hetzijzo.strucdocs.song;

import org.hetzijzo.strucdocs.song.domain.Song;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "/songs", collectionResourceRel = "songs", itemResourceRel = "song")
public interface SongRepository extends PagingAndSortingRepository<Song, UUID> {
}
