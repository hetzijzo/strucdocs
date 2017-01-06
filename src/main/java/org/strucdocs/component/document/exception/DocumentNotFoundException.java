package org.strucdocs.component.document.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends DocumentException {

    public DocumentNotFoundException(String message) {
        super(message);
    }
}
