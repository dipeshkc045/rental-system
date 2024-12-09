package com.bookrental.api.transaction.repository;

import com.bookrental.api.member.model.entity.Member;
import com.bookrental.api.transaction.model.entity.BookTransaction;
import com.bookrental.enums.RENT_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {
    BookTransaction findBookTransactionsByMemberIdAndRentStatus(Member memberId, RENT_TYPE rentStatus);
   List< BookTransaction> getBookTransactionByMemberIdAndActiveClosed(Member memberId, boolean activeClosed);
}
