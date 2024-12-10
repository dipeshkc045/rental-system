package com.bookrental.api.author.model.entity;

import com.bookrental.api.book.model.entity.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Hidden
    private Long id;

    @Schema(nullable = false,defaultValue = "dipesh chaudhary")
    private String name;

    @Schema(nullable = false,defaultValue = "dipeshc045@gmail.com",minLength = 13)
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @Schema(nullable = false,defaultValue = "9868099976",maxLength = 13, minLength = 10)
    private Long mobileNumber;


    @JsonBackReference
    @Hidden
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}
