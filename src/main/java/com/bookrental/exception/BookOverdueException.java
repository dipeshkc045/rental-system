package com.bookrental.exception;

import lombok.Getter;

@Getter
public class BookOverdueException extends RuntimeException {
    private final long daysLate;
    private final double fine;

    public BookOverdueException(String message, long daysLate, double fine) {
        super(message);
        this.daysLate = daysLate;
        this.fine = fine;
    }

}
