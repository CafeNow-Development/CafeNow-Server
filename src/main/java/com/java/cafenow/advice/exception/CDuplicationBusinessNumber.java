package com.java.cafenow.advice.exception;

public class CDuplicationBusinessNumber extends RuntimeException {
    public CDuplicationBusinessNumber(String msg, Throwable t) {
        super(msg, t);
    }
    public CDuplicationBusinessNumber(String msg) {
        super(msg);
    }
    public CDuplicationBusinessNumber() {
        super();
    }
}
