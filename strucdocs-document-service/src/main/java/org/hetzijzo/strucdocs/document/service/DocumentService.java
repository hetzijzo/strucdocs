package org.hetzijzo.strucdocs.document.service;

import lombok.extern.slf4j.Slf4j;

import org.hetzijzo.strucdocs.document.domain.StrucdocsDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@Slf4j
@Transactional(readOnly = true)
public class DocumentService {

    private final BinaryStoreService binaryStoreService;
    private final DataExtractionService dataExtractionService;
    private final StrucdocsDocumentRepository strucdocsDocumentRepository;

    public DocumentService(BinaryStoreService binaryStoreService, DataExtractionService dataExtractionService,
                           StrucdocsDocumentRepository strucdocsDocumentRepository) {
        this.binaryStoreService = binaryStoreService;
        this.dataExtractionService = dataExtractionService;
        this.strucdocsDocumentRepository = strucdocsDocumentRepository;
    }

    public Iterable<StrucdocsDocument> getAllDocuments() {
        return strucdocsDocumentRepository.findAll();
    }

    public StrucdocsDocument getDocumentByUuid(UUID uuid) {
        return strucdocsDocumentRepository.findOne(uuid);
    }

    @Transactional(readOnly = false)
    public StrucdocsDocument saveDocument(MultipartFile uploadedFile) {
        File file = binaryStoreService.save(uploadedFile);
        StrucdocsDocument strucdocsFile = dataExtractionService.extractMetadata(file);
        return strucdocsDocumentRepository.save(strucdocsFile);
    }
}
