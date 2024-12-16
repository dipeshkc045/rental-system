package com.bookrental.exception;

import com.bookrental.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ActiveRentalException.class)
    public ResponseEntity<ResponseDto> handleActiveRentalException(ActiveRentalException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("bookId", ex.getBookId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error("ACTIVE_RENTAL_EXISTS", errorDetails));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.error("INTERNAL_ERROR", Map.of("message", ex.getMessage())));
    }

    @ExceptionHandler(BookOverdueException.class)
    public ResponseEntity<ResponseDto> handleBookOverdueException(BookOverdueException ex) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(ResponseDto.error("BOOK_OVERDUE", Map.of("message", ex.getMessage(), "daysLate", ex.getDaysLate(), "fine", ex.getFine())));
    }
}
