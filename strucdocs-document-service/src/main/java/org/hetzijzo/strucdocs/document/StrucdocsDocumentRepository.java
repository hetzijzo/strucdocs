package org.hetzijzo.strucdocs.document;

import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrucdocsDocumentRepository extends CouchbasePagingAndSortingRepository<StrucdocsDocument, String> {
}
