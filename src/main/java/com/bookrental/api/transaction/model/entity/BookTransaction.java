package com.bookrental.api.transaction.model.entity;

import com.bookrental.api.book.model.entity.Book;
import com.bookrental.api.user.model.User;
import com.bookrental.enums.RENT_TYPE;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "booking_transaction",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code", name = "uk_transaction_code")
        },
        indexes = {
                @Index(columnList = "fromDate"),
                @Index(columnList = "toDate"),
                @Index(columnList = "rentStatus")
        }
)
public class BookTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_transaction_sequence_generator")
    @SequenceGenerator(
            name = "book_transaction_sequence_generator",
            sequenceName = "book_transaction_sequence",
            allocationSize = 1
    )
    @Hidden
    @Schema(description = "Unique identifier for the book transaction")
    private Long id;

    @NotBlank(message = "Transaction code is required")
    @Size(min = 6, max = 20, message = "Transaction code must be between 6 and 20 characters")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Transaction code must contain only uppercase letters, numbers, and hyphens")
    @Column(name = "transaction_code", nullable = false, unique = true, length = 20)
    @Schema(
            description = "Unique transaction code",
            example = "RENT-12345",
            nullable = false
    )
    private String code;

    @NotNull(message = "Start date is required")
    @Column(name = "from_date", nullable = false)
    @Schema(
            description = "Transaction start date",
            nullable = false
    )
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @NotNull(message = "End date is required")
    @Column(name = "to_date", nullable = false)
    @Schema(
            description = "Transaction end date",
            nullable = false
    )
    @Temporal(TemporalType.DATE)
    private Date toDate;

    @NotNull(message = "Rent status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "rent_status", nullable = false)
    @Schema(
            description = "Current status of the rental transaction",
            nullable = false
    )
    private RENT_TYPE rentStatus;

    @Column(name = "is_active_closed", nullable = false)
    @Schema(
            description = "Indicates if the transaction is actively closed",
            defaultValue = "false"
    )
    @Builder.Default
    private boolean activeClosed = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Hidden
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Hidden
    private LocalDateTime updatedAt;

    @NotNull(message = "Book is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "book_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction_book")
    )
    @Hidden
    @Schema(description = "Book associated with the transaction")
    private Book book;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction_user")
    )
    @Hidden
    @Schema(description = "User associated with the transaction")
    private User user;

    // Validation method to ensure end date is after start date
    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (fromDate != null && toDate != null && fromDate.after(toDate)) {
            throw new IllegalStateException("Start date must be before or equal to end date");
        }
    }
}
