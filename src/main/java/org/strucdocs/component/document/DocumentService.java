package org.strucdocs.component.document;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.strucdocs.model.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional(readOnly = true)
class DocumentService {

    private final FileStoreService fileStoreService;
    private final DocumentExtractionService documentExtractionService;
    private final DocumentRepository documentRepository;

    Iterable<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    Document getDocumentByUuid(UUID uuid) {
        return documentRepository.findOne(uuid);
    }

    InputStreamResource getDocumentContent(Document document)
        throws IOException {
        InputStream inputStream = fileStoreService.get(document.getFilename());
        return new InputStreamResource(inputStream);
    }

    @Transactional(readOnly = false)
    Document saveDocument(MultipartFile uploadedFile) {
        File file = fileStoreService.save(uploadedFile);
        Document strucdocsFile = documentExtractionService.extractMetadata(file);
        return documentRepository.save(strucdocsFile);
    }
}
