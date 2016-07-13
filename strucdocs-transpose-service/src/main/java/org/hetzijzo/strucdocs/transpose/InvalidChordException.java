package org.hetzijzo.strucdocs.transpose;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidChordException extends RuntimeException {

    InvalidChordException(String chordString) {
        super(String.format("Invalid chord %s ", chordString));
    }
}
