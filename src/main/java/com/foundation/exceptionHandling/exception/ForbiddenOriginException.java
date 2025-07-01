package com.foundation.exceptionHandling.exception;

public class ForbiddenOriginException extends RuntimeException {
    public ForbiddenOriginException(String origin) {
        super("Forbidden origin: " + origin);
    }
}
