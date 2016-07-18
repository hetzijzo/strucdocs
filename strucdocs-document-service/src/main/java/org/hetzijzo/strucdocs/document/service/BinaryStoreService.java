package org.hetzijzo.strucdocs.document.service;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class BinaryStoreService {

    private final File binaryStoreRoot;
    private final DataExtractionService dataExtractionService;
    private final SHA1Service sha1Service;

    public BinaryStoreService(@Value("${binaryStore.root:upload-dir}") String binaryStoreRoot,
                              DataExtractionService dataExtractionService, SHA1Service sha1Service) {
        this.binaryStoreRoot = new File(binaryStoreRoot);
        this.dataExtractionService = dataExtractionService;
        this.sha1Service = sha1Service;
    }

    //    public StrucdocsFile findFile(String digest) {
//        File f = new File(binaryStoreRoot, digest);
//        if (!f.exists()) {
//            return null;
//        }
//        JsonDocument doc = bucket.get(digest); //TODO: Delegate to Repository
//        if (doc == null) {
//            return null;
//        }
//        StoredFileDocument fileDoc = new StoredFileDocument(doc);
//        return new StrucdocsFile(f, fileDoc);
//    }
//
//    public boolean deleteFile(String digest) {
//        File f = new File(binaryStoreRoot, digest);
//        if (!f.exists()) {
//            throw new IllegalArgumentException("Can't delete file that does not exist");
//        }
//        boolean deleted = f.delete();
//        bucket.remove(digest); //TODO: Delegate to Repository
//        return deleted;
//    }
//
    public File storeFile(MultipartFile uploadedFile) {
        try {
            String digest = sha1Service.getSha1Digest(uploadedFile.getInputStream());
            File file = new File(binaryStoreRoot, digest);
            @Cleanup
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            FileCopyUtils.copy(uploadedFile.getInputStream(), stream);
            return file;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
