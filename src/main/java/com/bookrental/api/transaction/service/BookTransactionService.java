package com.bookrental.api.transaction.service;

import com.bookrental.api.transaction.model.entity.BookTransaction;
import com.bookrental.api.transaction.model.request.BookTransactionRequestDto;

public interface BookTransactionService {

    BookTransaction processBookTransaction(BookTransactionRequestDto bookTransactionRequestDto);
    void updateTransaction(BookTransactionRequestDto bookTransactionRequestDto);

}
