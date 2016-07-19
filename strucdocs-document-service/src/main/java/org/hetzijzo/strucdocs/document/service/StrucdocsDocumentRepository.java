package org.hetzijzo.strucdocs.document.service;

import org.hetzijzo.strucdocs.document.domain.StrucdocsDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StrucdocsDocumentRepository extends PagingAndSortingRepository<StrucdocsDocument, UUID> {
}
