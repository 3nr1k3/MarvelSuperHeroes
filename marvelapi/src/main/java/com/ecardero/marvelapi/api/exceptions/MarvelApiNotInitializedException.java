package com.ecardero.marvelapi.api.exceptions;

public class MarvelApiNotInitializedException extends RuntimeException {
    public MarvelApiNotInitializedException() {
        super();
    }

    public MarvelApiNotInitializedException(String message) {
        super(message);
    }

    public MarvelApiNotInitializedException(Throwable cause) {
        super(cause);
    }

    public MarvelApiNotInitializedException(String message, Throwable cause) {
        super(message, cause);
    }

}
