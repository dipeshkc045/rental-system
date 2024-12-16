package com.bookrental.api.transaction.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookTransactionResponseDto {
    private Long transactionId;
    private Long bookId;
    private Long userId;
    private String transactionDate;
    private String dueDate;
    private String status;


}
