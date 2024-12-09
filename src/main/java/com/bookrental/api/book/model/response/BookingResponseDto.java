package com.bookrental.api.book.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {
    private String name;
    private Integer noOfPages;
    private String isbn;
    private Double rating;
    private Integer stockCount;
    private Date stockDate;
    private String photo;
}
