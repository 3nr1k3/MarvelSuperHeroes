package com.ecardero.marvelapi.api.exceptions;

public class RateLimitException extends Exception {
    public RateLimitException() {
        super();
    }

    public RateLimitException(String message) {
        super(message);
    }

    public RateLimitException(Throwable cause) {
        super(cause);
    }

    public RateLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
