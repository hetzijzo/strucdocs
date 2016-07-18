package org.hetzijzo.strucdocs.document.service;

import lombok.extern.slf4j.Slf4j;

import org.hetzijzo.strucdocs.document.domain.StrucdocsFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
public class DocumentService {

    private final BinaryStoreService binaryStoreService;
    private final DataExtractionService dataExtractionService;
    private final StrucdocsFileRepository strucdocsFileRepository;

    public DocumentService(BinaryStoreService binaryStoreService, DataExtractionService dataExtractionService,
                           StrucdocsFileRepository strucdocsFileRepository) {
        this.binaryStoreService = binaryStoreService;
        this.dataExtractionService = dataExtractionService;
        this.strucdocsFileRepository = strucdocsFileRepository;
    }

    public Iterable<StrucdocsFile> getAllDocuments() {
        return strucdocsFileRepository.findAll();
    }

    public StrucdocsFile getDocumentById(String id) {
        return strucdocsFileRepository.findOne(id);
    }

    public void saveDocument(MultipartFile uploadedFile) {
        File file = binaryStoreService.storeFile(uploadedFile);
        StrucdocsFile strucdocsFile = dataExtractionService.extractMetadata(file);
        strucdocsFileRepository.save(strucdocsFile);
    }
}
