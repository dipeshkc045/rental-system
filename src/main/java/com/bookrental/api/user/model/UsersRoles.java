package com.bookrental.api.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(
        name = "user_roles_tb",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "role_id"},
                        name = "uk_user_role"
                )
        },
        indexes = {
                @Index(columnList = "user_id"),
                @Index(columnList = "role_id")
        }
)
public class UsersRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_tb_seq_gen")
    @SequenceGenerator(
            name = "user_role_tb_seq_gen",
            sequenceName = "user_role_tb_seq",
            allocationSize = 1
    )
    @Schema(description = "Unique identifier for user role assignment")
    private Long id;

    @Column(name = "is_active", nullable = false)
    @Schema(
            description = "Indicates if the role assignment is currently active",
            defaultValue = "true"
    )
    @Builder.Default
    private boolean isActive = true;

    @NotNull(message = "User cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_roles_user")
    )
    @JsonBackReference
    @Schema(description = "User associated with this role")
    private User user;

    @NotNull(message = "Role cannot be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_roles_role")
    )
    @Schema(description = "Role assigned to the user")
    private Roles role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Hidden
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Hidden
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.user == null) {
            throw new IllegalStateException("User must be set before persisting UsersRoles");
        }
        if (this.role == null) {
            throw new IllegalStateException("Role must be set before persisting UsersRoles");
        }
    }
}
