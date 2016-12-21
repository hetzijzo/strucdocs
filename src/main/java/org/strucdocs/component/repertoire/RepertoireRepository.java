package org.strucdocs.component.repertoire;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.strucdocs.model.Repertoire;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface RepertoireRepository extends PagingAndSortingRepository<Repertoire, UUID> {

    /**
     * Find all Repertoires by uuid of the Band.
     *
     * @param bandUuid The uuid of the Band.
     * @return List of Repertoire
     */
    List<Repertoire> findByBandUuid(@Param("bandUuid") UUID bandUuid);
}
