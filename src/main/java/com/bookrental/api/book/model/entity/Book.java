package com.bookrental.api.book.model.entity;

import com.bookrental.api.author.model.entity.Author;
import com.bookrental.api.category.model.entity.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence_generator")
    @SequenceGenerator(
            name = "book_sequence_generator",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @Hidden
    private Long id;

    private String name;
    private Integer noOfPages;
    @Column(unique = true)
    private String isbn;
    private Double rating;
    private Integer stockCount;
    private Date publishedDate;
    private String photo;


    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category categoryId;
}
