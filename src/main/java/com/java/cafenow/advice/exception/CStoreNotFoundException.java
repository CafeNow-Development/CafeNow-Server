package com.java.cafenow.advice.exception;

public class CStoreNotFoundException extends RuntimeException {
    public CStoreNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CStoreNotFoundException(String msg) {
        super(msg);
    }

    public CStoreNotFoundException() {
        super();
    }
}
