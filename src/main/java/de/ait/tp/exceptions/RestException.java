package de.ait.tp.exceptions;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {
    private HttpStatus status;

    public RestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
