package com.bookrental.api.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_tb",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email", name = "uk_user_email"),
                @UniqueConstraint(columnNames = "mobile_no", name = "uk_user_mobile")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_tb_seq_gen")
    @SequenceGenerator(
            name = "user_tb_seq_gen",
            sequenceName = "user_tb_seq",
            allocationSize = 1
    )
    @Hidden
    @Schema(description = "Unique identifier for the user")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 25, message = "Name must be between 3 and 25 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    @Column(name = "full_name", nullable = false, length = 25)
    @Schema(
            description = "User's full name",
            minLength = 3,
            maxLength = 25,
            nullable = false,
            defaultValue = "John Doe"
    )
    private String name;

    @NotBlank(message = "Email is required")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    @Schema(
            description = "User's email address",
            nullable = false,
            defaultValue = "johndoe@example.com"
    )
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%]).{8,}$",
            message = "Password must contain uppercase, lowercase, number, and special character")
    @Column(name = "password", nullable = false)
    @JsonIgnore
    @Schema(description = "User's password (will be hashed)", minLength = 8, maxLength = 255)
    private String password;

    @Pattern(regexp = "^\\+?[1-9]\\d{9,14}$", message = "Invalid mobile number")
    @Column(name = "mobile_no", unique = true, length = 15)
    @Schema(
            description = "User's mobile number",
            minLength = 10,
            maxLength = 15,
            defaultValue = "+9779868090501"
    )
    private String mobileNo;

    @Size(min = 3, max = 255, message = "Address must be between 3 and 255 characters")
    @Column(name = "address", length = 255)
    @Schema(
            description = "User's address",
            minLength = 3,
            maxLength = 255,
            defaultValue = "Kathmandu,Nepal"
    )
    private String address;

    @Column(name = "is_active", nullable = false)
    @Schema(description = "User account status", defaultValue = "true")
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Hidden
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Hidden
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Hidden
    private Set<UsersRoles> roles;
}
