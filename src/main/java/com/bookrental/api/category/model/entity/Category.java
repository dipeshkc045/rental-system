package com.bookrental.api.category.model.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence_generator")
    @SequenceGenerator(
            name = "category_sequence_generator",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @Hidden
    private Long id;
    private String name;
    private String description;

}
