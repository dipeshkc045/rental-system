package com.bookrental.exception;

import lombok.Getter;

@Getter
public class ActiveRentalException extends RuntimeException {
    private final Long bookId;

    public ActiveRentalException(String message, Long bookId) {
        super(message);
        this.bookId = bookId;
    }

}
