package com.bookrental.api.author.model.entity;

import com.bookrental.api.book.model.entity.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence_generator")
    @SequenceGenerator(
            name = "author_sequence_generator",
            sequenceName = "author_sequence",
            allocationSize = 1
    )
    private Long id;

    private String name;
    private String email;
    private Long mobileNumber;


    @JsonBackReference
    @Hidden
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}
