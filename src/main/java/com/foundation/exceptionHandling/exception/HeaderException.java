package com.foundation.exceptionHandling.exception;

public class HeaderException extends RuntimeException {
    public HeaderException(String header) {
        super(header);
    }
}
