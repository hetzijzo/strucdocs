package org.hetzijzo.strucdocs.document.rest;

import lombok.extern.slf4j.Slf4j;

import org.hetzijzo.strucdocs.document.domain.StrucdocsFile;
import org.hetzijzo.strucdocs.document.service.DocumentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("/")
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<StrucdocsFile> getDocuments() {
        return documentService.getAllDocuments();
    }

    @RequestMapping("{id}")
    public StrucdocsFile getDocumentById(@PathVariable String id) {
        return documentService.getDocumentById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveDocument(@RequestParam("file") MultipartFile file) throws IOException {
        documentService.saveDocument(file);
    }
}
