package org.hetzijzo.strucdocs.document.service;

import org.hetzijzo.strucdocs.document.domain.StrucdocsFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrucdocsFileRepository extends PagingAndSortingRepository<StrucdocsFile, String> {
}
