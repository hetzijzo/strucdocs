package org.strucdocs.component.document;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.strucdocs.model.Document;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resources<Resource<Document>>> getDocuments() {
        return ResponseEntity.ok(
            Resources.wrap(documentService.getAllDocuments())
        );
    }

    @RequestMapping(method = RequestMethod.GET, value = "{uuid}")
    public ResponseEntity<Resource<Document>> getDocumentByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(
            new Resource<>(documentService.getDocumentByUuid(uuid))
        );
    }

    @RequestMapping(method = RequestMethod.GET, value = "{uuid}/content")
    public ResponseEntity<InputStreamResource> getDocumentContentByUuid(@PathVariable UUID uuid)
        throws IOException {
        Document document = documentService.getDocumentByUuid(uuid);
        InputStreamResource documentStreamResource = documentService.getDocumentContent(document);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentLength(document.getSize())
            .contentType(MediaType.parseMediaType(document.getMimeType()))
            .body(documentStreamResource);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveDocument(@RequestParam("file") MultipartFile file)
        throws IOException {
        Document document = documentService.saveDocument(file);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .lastModified(document.getModifyDate().toEpochMilli())
            .build();
    }
}
