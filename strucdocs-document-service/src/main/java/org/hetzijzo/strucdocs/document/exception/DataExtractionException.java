package org.hetzijzo.strucdocs.document.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataExtractionException extends RuntimeException {

    public DataExtractionException(Throwable cause) {
        super(cause);
    }
}