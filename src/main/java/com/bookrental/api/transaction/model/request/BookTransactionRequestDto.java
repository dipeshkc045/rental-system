package com.bookrental.api.transaction.model.request;

import com.bookrental.enums.RENT_TYPE;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTransactionRequestDto {

    private Long bookId;
    @Hidden
    private String code;
    private Date fromDate;
    private Date toDate;
    @Enumerated(EnumType.STRING)
    private RENT_TYPE rentStatus;
    private boolean activeClosed;
}
