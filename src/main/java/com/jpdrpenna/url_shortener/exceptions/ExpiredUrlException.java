package com.jpdrpenna.url_shortener.exceptions;

public class ExpiredUrlException extends RuntimeException {
    public ExpiredUrlException(String message) {
        super(message);
    }
}
