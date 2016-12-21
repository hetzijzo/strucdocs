package org.strucdocs.component.musician;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.strucdocs.model.Musician;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(path = "musicians", collectionResourceRel = "musicians")
public interface MusicianRepository extends PagingAndSortingRepository<Musician, UUID> {

    /**
     * Find all Musician by uuid of the Band.
     *
     * @param bandUuid The uuid of the Band.
     * @return List of Musician
     */
    List<Musician> findByBandUuid(@Param("bandUuid") UUID bandUuid);
}
