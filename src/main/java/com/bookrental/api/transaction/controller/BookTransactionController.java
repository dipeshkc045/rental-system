package com.bookrental.api.transaction.controller;


import com.bookrental.api.transaction.model.request.BookTransactionRequestDto;
import com.bookrental.api.transaction.service.BookTransactionService;
import com.bookrental.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "BookTransaction APIs", description = "Book Transaction APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("transaction")
public class BookTransactionController {


    private final BookTransactionService bookTransactionService;

    @PostMapping
    public ResponseEntity<ResponseDto> createBook(
            @Valid @RequestBody BookTransactionRequestDto bookTransactionRequestDto
    ) {
        var rentedBook = bookTransactionService.rentBook(bookTransactionRequestDto);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("rent-book", rentedBook);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(responseData));
    }
}
