package com.securevpn.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServerUnavailableException extends RuntimeException {
    public ServerUnavailableException(String message) {
        super(message);
    }
}
