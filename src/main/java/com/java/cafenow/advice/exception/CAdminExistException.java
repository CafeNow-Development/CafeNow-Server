package com.java.cafenow.advice.exception;

public class CAdminExistException extends RuntimeException {
    public CAdminExistException(String msg, Throwable t) {
        super(msg, t);
    }
    public CAdminExistException(String msg) {
        super(msg);
    }
    public CAdminExistException() {
        super();
    }
}