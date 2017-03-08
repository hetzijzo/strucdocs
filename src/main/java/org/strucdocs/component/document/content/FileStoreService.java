package org.strucdocs.component.document.content;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.strucdocs.component.document.exception.DocumentException;
import org.strucdocs.component.document.exception.DocumentNotFoundException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
class FileStoreService {

    private final File root;

    boolean exists(String digest) {
        return new File(root, digest).exists();
    }

    InputStream get(String digest) {
        try {
            File file = new File(root, digest);
            return new BufferedInputStream(new FileInputStream(file));
        } catch (IOException ex) {
            throw new DocumentException(ex);
        }
    }

    File save(MultipartFile uploadedFile) {
        try {
            String digest = getSha1Digest(uploadedFile.getInputStream());
            File file = new File(root, digest);
            @Cleanup
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            FileCopyUtils.copy(uploadedFile.getInputStream(), stream);
            return file;
        } catch (IOException ex) {
            throw new DocumentException(ex);
        }
    }

    boolean delete(String digest) {
        File f = new File(root, digest);
        if (!f.exists()) {
            throw new DocumentNotFoundException(
                String.format("Unable to delete non-existing file %s", digest)
            );
        }
        return f.delete();
    }

    private String getSha1Digest(InputStream is) {
        try {
            return DigestUtils.sha1Hex(is);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
