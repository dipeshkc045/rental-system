package com.bookrental.api.book.service;

import com.bookrental.api.book.model.entity.Book;
import com.bookrental.api.book.model.request.BookRequestDto;

import java.util.List;

public interface BookService {

    Book saveBook(BookRequestDto bookRequestDto);


    List<Book> getAllBookings();


    Book getBookingById(Long id);

    Book updateBooking(Long id, BookRequestDto bookingRequestDto);


    void deleteBooking(Long id);
}
