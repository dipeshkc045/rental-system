package com.bookrental.api.excel.model.request;

import com.bookrental.enums.RENT_TYPE;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookTransactionRequestDTO {

    private Integer id;
    private String code;
    private Date fromDate;
    private Date toDate;
    private RENT_TYPE rentStatus;
    private boolean activeClosed;
    private Long bookId;
    private Long memberId;

}
