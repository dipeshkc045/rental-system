package com.bookrental.api.book.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequestDto {
    @NotBlank(message = "Book name is required")
    @Size(min = 3, max = 255, message = "Book name must be between 3 and 255 characters")
    private String name;

    @NotNull(message = "Number of pages is required")
    @Min(value = 1, message = "Book must have at least 1 page")
    @Max(value = 10000, message = "Book cannot exceed 10,000 pages")
    private Integer noOfPages;

    @Pattern(regexp = "ISBN(?:-13)?:?\\x20*(?=.{17}$)97(?:8|9)([ -])\\d{1,5}\\1\\d{1,7}\\1\\d{1,6}\\1\\d$",
            message = "Invalid ISBN format")
    private String isbn;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5")
    private Double rating;

    @NotNull(message = "Stock count is required")
    @Min(value = 0, message = "Stock count cannot be negative")
    @Max(value = 10000, message = "Stock count cannot exceed 10,000")
    private Integer stockCount;

    @NotNull(message = "Published date is required")
    @PastOrPresent(message = "Published date must be in the past or present")
    private LocalDate publishedDate;

    @Size(max = 500, message = "Photo URL cannot exceed 500 characters")
    private String photo;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be a positive number")
    private Long categoryId;

    @NotEmpty(message = "At least one author is required")
    @Size(max = 10, message = "Maximum 10 authors are allowed")
    private List<@NotNull(message = "Author ID cannot be null") @Positive(message = "Author ID must be a positive number") Long> authors;
}
