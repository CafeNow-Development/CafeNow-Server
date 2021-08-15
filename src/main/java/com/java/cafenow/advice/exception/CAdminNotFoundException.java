package com.java.cafenow.advice.exception;

public class CAdminNotFoundException extends RuntimeException{
    public CAdminNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CAdminNotFoundException(String msg) {
        super(msg);
    }

    public CAdminNotFoundException() {
        super();
    }
}
