package com.bookrental.api.author.model.entity;

import com.bookrental.api.book.model.entity.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "author",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email", name = "uk_author_email"),
                @UniqueConstraint(columnNames = "mobile_number", name = "uk_author_mobile")
        }
)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence_generator")
    @SequenceGenerator(
            name = "author_sequence_generator",
            sequenceName = "author_sequence",
            allocationSize = 1
    )
    @Hidden
    @Schema(description = "Unique identifier for the author")
    private Long id;

    @NotBlank(message = "Author name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    @Column(name = "full_name", nullable = false, length = 100)
    @Schema(
            description = "Full name of the author",
            example = "Dipesh Chaudhary",
            minLength = 3,
            maxLength = 100,
            nullable = false
    )
    private String name;

    @NotBlank(message = "Email is required")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    @Column(name = "email", unique = true, nullable = false, length = 100)
    @Schema(
            description = "Author's email address",
            example = "dipeshc045@gmail.com",
            nullable = false
    )
    private String email;

    @NotNull(message = "Mobile number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{9,14}$", message = "Invalid mobile number")
    @Column(name = "mobile_number", unique = true, nullable = false, length = 15)
    @Schema(
            description = "Author's mobile number",
            example = "+9779868099976",
            minLength = 10,
            maxLength = 15,
            nullable = false
    )
    private String mobileNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Hidden
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Hidden
    private LocalDateTime updatedAt;

    @JsonBackReference
    @Hidden
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Book> books = new HashSet<>();

    @Column(name = "is_active", nullable = false)
    @Schema(description = "Author account status", defaultValue = "true")
    @Builder.Default
    private boolean isActive = true;
}
