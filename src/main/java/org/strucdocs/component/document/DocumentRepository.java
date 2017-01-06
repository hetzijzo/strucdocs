package org.strucdocs.component.document;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.strucdocs.model.Document;

import java.util.UUID;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, UUID> {
}
