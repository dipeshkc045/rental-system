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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenericMapper genericMapper;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public Book saveBook(BookRequestDto bookRequestDto) {
        Category category = categoryRepository.findById(bookRequestDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Author> authorList = authorRepository.findAllById(bookRequestDto.getAuthors());

        Book book = modelMapper.map(bookRequestDto, Book.class);
        book.setCategoryId(category);
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

    @Override
    public Book updateBooking(Long id, BookRequestDto bookingRequestDto) {
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        if (bookingRequestDto.getCategoryId() == null) {
            throw new RuntimeException("Category is required.");
        }

        Category category = categoryRepository.findById(bookingRequestDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Book updatedBook = Book.builder()
                .id(book.getId())
                .name(bookingRequestDto.getName() != null ? bookingRequestDto.getName() : book.getName())
                .noOfPages(bookingRequestDto.getNoOfPages() != null ? bookingRequestDto.getNoOfPages() : book.getNoOfPages())
                .isbn(bookingRequestDto.getIsbn() != null ? bookingRequestDto.getIsbn() : book.getIsbn())
                .rating(bookingRequestDto.getRating() != null ? bookingRequestDto.getRating() : book.getRating())
                .stockCount(bookingRequestDto.getStockCount() != null ? bookingRequestDto.getStockCount() : book.getStockCount())
                .publishedDate(bookingRequestDto.getPublishedDate() != null ? bookingRequestDto.getPublishedDate() : book.getPublishedDate())
                .photo(bookingRequestDto.getPhoto() != null ? bookingRequestDto.getPhoto() : book.getPhoto())
                .categoryId(category)
                .build();
        return bookRepository.save(updatedBook);
    }

    @Override
    public void deleteBooking(Long id) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        bookRepository.deleteById(existingBook.getId());

    }
}
