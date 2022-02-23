package com.assessment.demo.exception;

public class UserTransactionExecption extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserTransactionExecption(String resource) {
        super(resource != null ? resource : null);
    }
}
