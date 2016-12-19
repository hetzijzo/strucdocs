package org.strucdocs.component.repertoire;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.strucdocs.model.Repertoire;

import java.util.UUID;

@RepositoryRestResource
public interface RepertoireRepository extends PagingAndSortingRepository<Repertoire, UUID> {
}
