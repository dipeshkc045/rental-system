package com.bookrental.api.transaction.service;

import com.bookrental.api.transaction.model.request.BookTransactionRequestDto;
import com.bookrental.api.transaction.model.response.BookTransactionResponseDto;

public interface BookTransactionService {

    BookTransactionResponseDto processBookTransaction(BookTransactionRequestDto bookTransactionRequestDto);
    void updateTransaction(BookTransactionRequestDto bookTransactionRequestDto);

}
