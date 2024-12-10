package com.bookrental.api.transaction.service.impl;

import com.bookrental.api.book.model.entity.Book;
import com.bookrental.api.book.model.request.BookRequestDto;
import com.bookrental.api.book.repository.BookRepository;
import com.bookrental.api.book.service.BookService;
import com.bookrental.api.transaction.model.entity.BookTransaction;
import com.bookrental.api.transaction.model.request.BookTransactionRequestDto;
import com.bookrental.api.transaction.repository.BookTransactionRepository;
import com.bookrental.api.transaction.service.BookTransactionService;
import com.bookrental.api.user.model.User;
import com.bookrental.api.user.service.UserService;
import com.bookrental.enums.RENT_TYPE;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookTransactionServiceImpl implements BookTransactionService {

    private final BookTransactionRepository bookTransactionRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final UserService userService;

    @Override
    @Transactional
    public BookTransaction processBookTransaction(BookTransactionRequestDto requestDto) {
        Book book = getBookById(requestDto.getBookId());
        validateBookAvailability(book);

        User user = getAuthenticatedUser();
        validateActiveTransaction(user, requestDto.getRentStatus());

        if (requestDto.getRentStatus().equals(RENT_TYPE.RENT)) {
            validateRentDate(requestDto);
            return handleBookRenting(requestDto, book, user);
        } else if (requestDto.getRentStatus().equals(RENT_TYPE.RETURN)) {
            validateReturnDateInPast(requestDto);
            validateReturnDate(requestDto, book);
            return handleBookReturning(requestDto, book);
        } else {
            throw new IllegalArgumentException("Unknown rent status");
        }
    }

    private Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    private void validateBookAvailability(Book book) {
        if (book.getStockCount() <= 0) {
            throw new RuntimeException("Book is not available for rent");
        }
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        return userService.getByUsername(authentication.getName());
    }

    private void validateActiveTransaction(User user, RENT_TYPE rentStatus) {
        BookTransaction activeTransaction = bookTransactionRepository.findBookTransactionsByUserAndRentStatus(user, RENT_TYPE.RENT);
        if (activeTransaction != null && rentStatus == RENT_TYPE.RENT) {
            throw new RuntimeException(String.format("You must return the rented book with ID %d before renting a new book", activeTransaction.getBook().getId()));
        }
    }

    private BookTransaction handleBookRenting(BookTransactionRequestDto requestDto, Book book, User user) {
        BookTransaction transaction = BookTransaction.builder()
                .book(book)
                .user(user)
                .code(UUID.randomUUID().toString())
                .fromDate(requestDto.getFromDate())
                .toDate(requestDto.getToDate())
                .rentStatus(RENT_TYPE.RENT)
                .activeClosed(requestDto.isActiveClosed())
                .build();

        BookTransaction savedTransaction = bookTransactionRepository.save(transaction);
        updateBookStock(book, -1);
        return savedTransaction;
    }

    private BookTransaction handleBookReturning(BookTransactionRequestDto requestDto, Book book) {
        updateTransaction(requestDto);
        updateBookStock(book, 1);
        return BookTransaction.builder().book(book).build();
    }

    private void updateBookStock(Book book, int adjustment) {
        BookRequestDto updatedBookRequestDto = BookRequestDto.builder()
                .stockCount(book.getStockCount() + adjustment)
                .build();
        updatedBookRequestDto.setCategoryId(book.getCategory().getId());
        bookService.updateBooking(book.getId(), updatedBookRequestDto);
    }

    @Override
    public void updateTransaction(BookTransactionRequestDto requestDto) {
        BookTransaction transaction = bookTransactionRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setRentStatus(RENT_TYPE.RETURN);
        transaction.setActiveClosed(false);
        bookTransactionRepository.save(transaction);
    }


    private void validateRentDate(BookTransactionRequestDto requestDto) {
        LocalDate currentDate = LocalDate.now();
        LocalDate rentDate = requestDto.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (rentDate.isBefore(currentDate)) {
            throw new RuntimeException("Cannot rent a book for a date in the past");
        }
    }

    private void validateReturnDateInPast(BookTransactionRequestDto requestDto) {
        LocalDate currentDate = LocalDate.now();
        LocalDate returnDate = requestDto.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (returnDate.isBefore(currentDate)) {
            throw new RuntimeException("Cannot return the book with a past return date");
        }
    }

    private void validateReturnDate(BookTransactionRequestDto requestDto, Book book) {
        BookTransaction activeTransaction = bookTransactionRepository.findBookTransactionsByBookAndActiveClosedAndRentStatus(book,RENT_TYPE.RENT,true);
        if (activeTransaction != null) {
            LocalDate dueDate = activeTransaction.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate returnLocalDate = requestDto.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (returnLocalDate.isAfter(dueDate)) {
                long daysLate = Duration.between(dueDate.atStartOfDay(), returnLocalDate.atStartOfDay()).toDays();
                double fine = calculateFine(daysLate);
                throw new RuntimeException("The book is overdue by " + daysLate + " days. Fine amount: " + fine);
            }
        }
    }

    private double calculateFine(long daysLate) {
        double finePerDay = 2.0;
        return daysLate * finePerDay;
    }
}
