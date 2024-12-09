package com.bookrental.api.transaction.model.entity;

import com.bookrental.api.book.model.entity.Book;
import com.bookrental.api.member.model.entity.Member;
import com.bookrental.api.user.model.User;
import com.bookrental.enums.RENT_TYPE;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking_transaction")
public class BookTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_transaction_sequence_generator")
    @SequenceGenerator(
            name = "book_transaction_sequence_generator",
            sequenceName = "book_transaction_sequence",
            allocationSize = 1
    )
    @Hidden
    private Long id;

    @Hidden
    private String code;
    private Date fromDate;
    private Date toDate;
    @Enumerated(EnumType.STRING)
    private RENT_TYPE rentStatus;
    private boolean activeClosed;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @Hidden
    private Book book;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Hidden
    private User user;
}
