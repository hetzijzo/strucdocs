package org.hetzijzo.strucdocs.transpose;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidChordException extends RuntimeException {

    public InvalidChordException(String chordString) {
        super(String.format("Invalid chord %s ", chordString));
    }
}
