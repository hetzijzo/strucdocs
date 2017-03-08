package org.strucdocs.component.document.content;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.strucdocs.component.document.DocumentRepository;
import org.strucdocs.model.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional(readOnly = true)
public class DocumentContentService {

    private final FileStoreService fileStoreService;
    private final DocumentExtractionService documentExtractionService;
    private final DocumentRepository documentRepository;

    public InputStreamResource getDocumentContent(Document document)
        throws IOException {
        InputStream inputStream = fileStoreService.get(document.getFilename());
        return new InputStreamResource(inputStream);
    }

    @Transactional(readOnly = false)
    public Document saveDocument(MultipartFile uploadedFile) {
        File file = fileStoreService.save(uploadedFile);
        Document strucdocsFile = documentExtractionService.extractMetadata(file);
        return documentRepository.save(strucdocsFile);
    }
}
