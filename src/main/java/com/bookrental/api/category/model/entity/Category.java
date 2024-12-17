package com.bookrental.api.category.model.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uk_category_name")
        }
)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence_generator")
    @SequenceGenerator(
            name = "category_sequence_generator",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @Hidden
    @Schema(description = "Unique identifier for the category")
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 25, message = "Category name must be between 3 and 25 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Category name must contain only letters and spaces")
    @Column(name = "category_name", unique = true, nullable = false, length = 25)
    @Schema(
            description = "Name of the book category",
            defaultValue = "Fiction",
            minLength = 3,
            maxLength = 25,
            nullable = false
    )
    private String name;

    @Size(min = 3, max = 60, message = "Category description must be between 3 and 60 characters")
    @Column(name = "category_description", length = 60)
    @Schema(
            description = "Description of the book category",
            defaultValue = "Books of imaginative fiction",
            minLength = 3,
            maxLength = 60
    )
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Hidden
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Hidden
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    @Schema(description = "Category active status", defaultValue = "true")
    @Builder.Default
    private boolean isActive = true;
}
