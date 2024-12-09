package com.bookrental.api.book.controller;

import com.bookrental.api.book.model.request.BookRequestDto;
import com.bookrental.api.book.service.BookService;
import com.bookrental.endpoints.EndPointConstants;
import com.bookrental.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Book", description = "Book management APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPointConstants.BOOK)
public class BookController {

    private final BookService bookService;

    @PostMapping(EndPointConstants.SAVE)
    public ResponseEntity<ResponseDto> createBook(
            @Valid @RequestBody BookRequestDto bookRequestDto
    ) {
        var savedBook = bookService.saveBook(bookRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("book", savedBook);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(responseData));
    }

    @GetMapping(EndPointConstants.GET_ALL)
    public ResponseEntity<ResponseDto> getAllBooks() {
        var books = bookService.getAllBookings();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("books", books);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @GetMapping(EndPointConstants.GET_BY_ID)
    public ResponseEntity<ResponseDto> getBookById(@PathVariable Long id) {
        var book = bookService.getBookingById(id);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("book", book);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @PutMapping(EndPointConstants.UPDATE)
    public ResponseEntity<ResponseDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDto bookRequestDto
    ) {
        var updatedBook = bookService.updateBooking(id, bookRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("book", updatedBook);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @DeleteMapping(EndPointConstants.DELETE)
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable Long id) {
        bookService.deleteBooking(id);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
