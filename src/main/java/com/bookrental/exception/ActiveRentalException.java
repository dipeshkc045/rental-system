package com.bookrental.exception;

public class ActiveRentalException extends RuntimeException {
    private final Long bookId;

    public ActiveRentalException(String message, Long bookId) {
        super(message);
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }
}
