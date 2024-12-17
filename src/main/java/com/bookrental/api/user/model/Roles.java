package com.bookrental.api.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles",
        uniqueConstraints = @UniqueConstraint(columnNames = "name", name = "uk_role_name"))
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_tb_seq_gen")
    @SequenceGenerator(name = "role_tb_seq_gen", sequenceName = "role_seq_gen", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Role name cannot be blank")
    @Size(min = 3, max = 25, message = "Role name must be between 3 and 25 characters")
    @Pattern(regexp = "^[A-Za-z_]+$", message = "Role name must contain only letters and underscores")
    @Column(name = "role_name", unique = true, nullable = false, length = 25)
    @Schema(
            description = "Name of the role",
            example = "ADMIN",
            minLength = 3,
            maxLength = 25,
            nullable = false
    )
    private String name;

    @NotNull(message = "Active status must be specified")
    @Column(name = "is_active", nullable = false)
    @Schema(
            description = "Indicates if the role is currently active",
            defaultValue = "true",
            nullable = false
    )
    private Boolean isActive = Boolean.TRUE;

}
