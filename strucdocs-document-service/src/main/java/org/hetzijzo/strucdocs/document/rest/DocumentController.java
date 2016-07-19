package org.hetzijzo.strucdocs.document.rest;

import org.hetzijzo.strucdocs.document.domain.StrucdocsDocument;
import org.hetzijzo.strucdocs.document.service.BinaryStoreService;
import org.hetzijzo.strucdocs.document.service.DocumentService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController("/")
public class DocumentController {

    private final DocumentService documentService;
    private final BinaryStoreService binaryStoreService;

    public DocumentController(DocumentService documentService, BinaryStoreService binaryStoreService) {
        this.documentService = documentService;
        this.binaryStoreService = binaryStoreService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<StrucdocsDocument> getDocuments() {
        return documentService.getAllDocuments();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{uuid}")
    public StrucdocsDocument getDocumentByUuid(@PathVariable UUID uuid) {
        return documentService.getDocumentByUuid(uuid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{uuid}/content")
    public ResponseEntity<InputStreamResource> getDocumentContentByUuid(@PathVariable UUID uuid)
        throws IOException {
        StrucdocsDocument document = documentService.getDocumentByUuid(uuid);
        InputStream inputStream = binaryStoreService.get(document.getFilename());

        return ResponseEntity.status(HttpStatus.OK)
            .contentLength(document.getSize())
            .contentType(MediaType.parseMediaType(document.getMimeType()))
            .body(new InputStreamResource(inputStream));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveDocument(@RequestParam("file") MultipartFile file)
        throws IOException {
        StrucdocsDocument document = documentService.saveDocument(file);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .lastModified(document.getModifyDate().toEpochMilli())
            .build();
    }
}
