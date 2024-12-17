package com.bookrental.api.book.service.impl;

import com.bookrental.api.author.model.entity.Author;
import com.bookrental.api.author.repository.AuthorRepository;
import com.bookrental.api.book.model.entity.Book;
import com.bookrental.api.book.model.request.BookRequestDto;
import com.bookrental.api.book.repository.BookRepository;
import com.bookrental.api.book.service.BookService;
import com.bookrental.api.category.model.entity.Category;
import com.bookrental.api.category.repository.CategoryRepository;
import com.bookrental.resourceconverter.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final GenericMapper genericMapper;

    @Transactional
    @Override
    public Book saveBook(BookRequestDto bookRequestDto) {
        Category category = categoryRepository.findById(bookRequestDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Author> authorList = authorRepository.findAllById(bookRequestDto.getAuthors());
        Book book = genericMapper.convert(bookRequestDto, Book.class);
        book.setCategory(category);
        book.setAuthors(authorList);

        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBookings() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookingById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Book updateBooking(Long id, BookRequestDto bookingRequestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (bookingRequestDto.getCategoryId() == null) {
            throw new RuntimeException("Category is required.");
        }

        Category category = categoryRepository.findById(bookingRequestDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        genericMapper.convert(bookingRequestDto, Book.class);
        book.setCategory(category);

        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBooking(Long id) {
        bookRepository.deleteById(id);
    }
}
