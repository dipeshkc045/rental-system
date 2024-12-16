package com.bookrental.exception;

public class ActiveTransactionException extends RuntimeException {
    public ActiveTransactionException(String message) {
        super(message);
    }
}
