package com.bookrental.api.book.model.entity;


import com.bookrental.api.author.model.entity.Author;
import com.bookrental.api.category.model.entity.Category;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "book",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "isbn", name = "uk_book_isbn")
        },
        indexes = {
                @Index(columnList = "name"),
                @Index(columnList = "category_id")
        }
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence_generator")
    @SequenceGenerator(
            name = "book_sequence_generator",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @Hidden
    @Schema(description = "Unique identifier for the book")
    private Long id;

    @NotBlank(message = "Book name is required")
    @Size(min = 3, max = 255, message = "Book name must be between 3 and 255 characters")
    @Column(name = "book_name", nullable = false, length = 255)
    @Schema(
            description = "Title of the book",
            defaultValue = "The Great Adventure",
            minLength = 3,
            maxLength = 255,
            nullable = false
    )
    private String name;

    @NotNull(message = "Number of pages is required")
    @Min(value = 1, message = "Book must have at least 1 page")
    @Max(value = 10000, message = "Book cannot exceed 10,000 pages")
    @Column(name = "number_of_pages", nullable = false)
    @Schema(
            description = "Total number of pages in the book",
            defaultValue = "300",
            nullable = false
    )
    private Integer noOfPages;

    @Pattern(regexp = "ISBN(?:-13)?:?\\x20*(?=.{17}$)97(?:8|9)([ -])\\d{1,5}\\1\\d{1,7}\\1\\d{1,6}\\1\\d$",
            message = "Invalid ISBN format")
    @Column(name = "isbn", unique = true, length = 30)
    @Schema(description = "International Standard Book Number", example = "ISBN-13: 978-1-4028-9462-6")
    private String isbn;


    @Schema(
            description = "Book rating out of 5",
            defaultValue = "4.5",
            nullable = true
    )
    private Double rating;

    @NotNull(message = "Stock count is required")
    @Min(value = 0, message = "Stock count cannot be negative")
    @Max(value = 10000, message = "Stock count cannot exceed 10,000")
    @Column(name = "stock_count", nullable = false)
    @Schema(
            description = "Number of books available in stock",
            defaultValue = "5",
            nullable = false
    )
    private Integer stockCount;

    @NotNull(message = "Published date is required")
    @Column(name = "published_date", nullable = false)
    @Schema(
            defaultValue = "Date when the book was published",
            nullable = false
    )
    private LocalDate publishedDate;

    @Column(name = "photo_url", length = 500)
    @Schema(description = "URL or path to book's photo")
    private String photo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Hidden
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Hidden
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    @Schema(description = "Book availability status", defaultValue = "true")
    @Builder.Default
    private boolean isActive = true;


    @NotEmpty(message = "At least one author is required")
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "author_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "author_id"})
    )
    private List<Author> authors = new ArrayList<>();


    @NotNull(message = "Category is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_book_category"))
    @Schema(description = "Book's category")
    private Category category;


    public void addAuthor(Author author) {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        if (!authors.contains(author)) {
            authors.add(author);
        }
    }
}
