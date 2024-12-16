package com.bookrental.exception;

public class BookOverdueException extends RuntimeException {
    private final long daysLate;
    private final double fine;

    public BookOverdueException(String message, long daysLate, double fine) {
        super(message);
        this.daysLate = daysLate;
        this.fine = fine;
    }

    public long getDaysLate() {
        return daysLate;
    }

    public double getFine() {
        return fine;
    }
}
