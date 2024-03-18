package com.sparta.library.exception.loan;

public class ForbiddenLoanException extends RuntimeException {
    public ForbiddenLoanException(String message) {
        super(message);
    }
}
