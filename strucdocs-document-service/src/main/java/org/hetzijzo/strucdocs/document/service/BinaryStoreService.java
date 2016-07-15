package org.hetzijzo.strucdocs.document.service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

import org.hetzijzo.strucdocs.document.StoredFile;
import org.hetzijzo.strucdocs.document.StoredFileDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class BinaryStoreService {

    private final File binaryStoreRoot;
    private final DataExtractionService dataExtractionService;
    private final SHA1Service sha1Service;

    private final Bucket bucket;

    @Autowired
    public BinaryStoreService(@Value("${binaryStore.root:upload-dir}") String binaryStoreRoot, Bucket bucket,
                              DataExtractionService dataExtractionService, SHA1Service sha1Service) {
        this.binaryStoreRoot = new File(binaryStoreRoot);
        this.bucket = bucket;
        this.dataExtractionService = dataExtractionService;
        this.sha1Service = sha1Service;
    }

    public StoredFile findFile(String digest) {
        File f = new File(binaryStoreRoot, digest);
        if (!f.exists()) {
            return null;
        }
        JsonDocument doc = bucket.get(digest); //TODO: Delegate to Repository
        if (doc == null) {
            return null;
        }
        StoredFileDocument fileDoc = new StoredFileDocument(doc);
        return new StoredFile(f, fileDoc);
    }

    public boolean deleteFile(String digest) {
        File f = new File(binaryStoreRoot, digest);
        if (!f.exists()) {
            throw new IllegalArgumentException("Can't delete file that does not exist");
        }
        boolean deleted = f.delete();
        bucket.remove(digest); //TODO: Delegate to Repository
        return deleted;
    }

    public void storeFile(String name, MultipartFile uploadedFile) {
        if (!uploadedFile.isEmpty()) {
            try {
                String digest = sha1Service.getSha1Digest(uploadedFile.getInputStream()); //TODO: This should be UUID?
                File file = new File(binaryStoreRoot, digest);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
                FileCopyUtils.copy(uploadedFile.getInputStream(), stream);
                stream.close();

                JsonObject metadata = dataExtractionService.extractMetadata(file);
                metadata.put(StoredFileDocument.BINARY_STORE_DIGEST_PROPERTY, digest);
                metadata.put("type", StoredFileDocument.COUCHBASE_STORED_FILE_DOCUMENT_TYPE);
                metadata.put(StoredFileDocument.BINARY_STORE_LOCATION_PROPERTY, name);
                metadata.put(StoredFileDocument.BINARY_STORE_FILENAME_PROPERTY, uploadedFile.getOriginalFilename());
                String mimeType = metadata.getString(StoredFileDocument.BINARY_STORE_METADATA_MIMETYPE_PROPERTY);
                if (MediaType.APPLICATION_PDF_VALUE.equals(mimeType)) {
                    String fulltextContent = dataExtractionService.extractText(file);
                    metadata.put(StoredFileDocument.BINARY_STORE_METADATA_FULLTEXT_PROPERTY, fulltextContent);
                }

                //TODO: Delegate to Repository
                JsonDocument doc = JsonDocument.create(digest, metadata);
                bucket.upsert(doc);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("File empty");
        }
    }
}
