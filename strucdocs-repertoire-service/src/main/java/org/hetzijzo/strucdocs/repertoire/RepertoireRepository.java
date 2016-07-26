package org.hetzijzo.strucdocs.repertoire;

import org.hetzijzo.strucdocs.repertoire.domain.Repertoire;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface RepertoireRepository extends PagingAndSortingRepository<Repertoire, UUID> {
}
