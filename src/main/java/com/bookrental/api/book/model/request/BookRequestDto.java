package com.bookrental.api.book.model.request;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequestDto {

    private String name;
    private Integer noOfPages;
    private String isbn;
    private Double rating;
    private Integer stockCount;
    private Date publishedDate;
    private String photo;
    private Long categoryId;
    private List<Long> authors;
}
