package org.strucdocs.component.document;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.strucdocs.model.Document;

import java.util.UUID;

@RepositoryRestResource(path = "documents", collectionResourceRel = "documents")
public interface DocumentRepository extends PagingAndSortingRepository<Document, UUID> {
}
