package org.hetzijzo.strucdocs.document.service;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import org.hetzijzo.strucdocs.document.exception.DocumentException;
import org.hetzijzo.strucdocs.document.exception.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class BinaryStoreService {

    private final File binaryStoreRoot;
    private final SHA1Service sha1Service;

    public BinaryStoreService(@Value("${binaryStore.root:upload-dir}") String binaryStoreRoot,
                              SHA1Service sha1Service) {
        this.binaryStoreRoot = new File(binaryStoreRoot);
        this.sha1Service = sha1Service;
    }

    public boolean exists(String digest) {
        return new File(binaryStoreRoot, digest).exists();
    }

    public InputStream get(String digest) {
        try {
            File file = new File(binaryStoreRoot, digest);
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
            return stream;
        } catch (IOException ex) {
            throw new DocumentException(ex);
        }
    }

    public File save(MultipartFile uploadedFile) {
        try {
            String digest = sha1Service.getSha1Digest(uploadedFile.getInputStream());
            File file = new File(binaryStoreRoot, digest);
            @Cleanup
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            FileCopyUtils.copy(uploadedFile.getInputStream(), stream);
            return file;
        } catch (IOException ex) {
            throw new DocumentException(ex);
        }
    }

    public boolean delete(String digest) {
        File f = new File(binaryStoreRoot, digest);
        if (!f.exists()) {
            throw new DocumentNotFoundException(
                String.format("Unable to delete non-existing file %s", digest)
            );
        }
        return f.delete();
    }
}
