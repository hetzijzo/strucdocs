package org.strucdocs.component.song;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.strucdocs.model.Song;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(path = "songs", collectionResourceRel = "songs", itemResourceRel = "song")
public interface SongRepository extends PagingAndSortingRepository<Song, UUID> {

    List<Song> findByArtistName(@Param("artist.name") String artistName);
}
