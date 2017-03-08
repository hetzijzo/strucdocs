package org.strucdocs.component.document.content;

import lombok.RequiredArgsConstructor;

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
import org.strucdocs.component.document.DocumentRepository;
import org.strucdocs.model.Document;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/documents/content")
@RequiredArgsConstructor
public class DocumentContentController {

    private final DocumentContentService documentContentService;
    private final DocumentRepository documentRepository;

    @RequestMapping(method = RequestMethod.GET, value = "{uuid}")
    public ResponseEntity<InputStreamResource> getDocumentContentByUuid(@PathVariable UUID uuid)
        throws IOException {
        Document document = documentRepository.findOne(uuid);
        InputStreamResource documentStreamResource = documentContentService.getDocumentContent(document);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentLength(document.getSize())
            .contentType(MediaType.parseMediaType(document.getMimeType()))
            .body(documentStreamResource);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveDocument(@RequestParam("file") MultipartFile file)
        throws IOException {
        Document document = documentContentService.saveDocument(file);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .lastModified(document.getModifyDate().toEpochMilli())
            .build();
    }
}
