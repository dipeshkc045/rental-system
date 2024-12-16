package com.bookrental.api.transaction.repository;

import com.bookrental.api.book.model.entity.Book;
import com.bookrental.api.transaction.model.entity.BookTransaction;
import com.bookrental.api.user.model.User;
import com.bookrental.enums.RENT_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {
  BookTransaction findBookTransactionsByUserAndRentStatus(User user, RENT_TYPE rentStatus);
   BookTransaction findBookTransactionsByBookAndActiveClosedAndRentStatus(Book book, boolean activeClosed, RENT_TYPE rentStatus);
}
