package org.strucdocs.component.band;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.strucdocs.model.Band;

import java.util.UUID;

@RepositoryRestResource(path = "bands", collectionResourceRel = "bands")
public interface BandRepository extends PagingAndSortingRepository<Band, UUID> {
}
