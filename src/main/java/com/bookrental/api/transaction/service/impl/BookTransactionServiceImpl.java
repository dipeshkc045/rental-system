package com.bookrental.api.transaction.service.impl;

import com.bookrental.api.book.model.entity.Book;
import com.bookrental.api.book.model.request.BookRequestDto;
import com.bookrental.api.book.repository.BookRepository;
import com.bookrental.api.book.service.BookService;
import com.bookrental.api.member.model.entity.Member;
import com.bookrental.api.member.service.MemberService;
import com.bookrental.api.transaction.model.entity.BookTransaction;
import com.bookrental.api.transaction.model.request.BookTransactionRequestDto;
import com.bookrental.api.transaction.repository.BookTransactionRepository;
import com.bookrental.api.transaction.service.BookTransactionService;
import com.bookrental.enums.RENT_TYPE;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookTransactionServiceImpl implements BookTransactionService {

    private final BookTransactionRepository bookTransactionRepository;
    private final MemberService memberService;
    private final BookRepository bookRepository;
    private final BookService bookService;


    @Override
    @Transactional
    public BookTransaction rentBook(BookTransactionRequestDto bookTransactionRequestDto) {

        Book existBook = bookRepository.findById(bookTransactionRequestDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));


        if (existBook.getStockCount() > 0) {

            Member existMember = memberService.getMemberById(bookTransactionRequestDto.getMemberId());
            BookTransaction activeBookTransaction = bookTransactionRepository.findBookTransactionsByMemberIdAndRentStatus(existMember, RENT_TYPE.RENT);
            if (activeBookTransaction != null && (bookTransactionRequestDto.getRentStatus())==RENT_TYPE.RENT) {
                log.info("you have to return the previously rented book with Id : {} in order to rent new book", activeBookTransaction.getBookId());
            } else {
                BookTransaction bookTransaction = BookTransaction.builder()
                        .bookId(existBook)
                        .memberId(existMember)
                        .code(UUID.randomUUID().toString())
                        .fromDate(bookTransactionRequestDto.getFromDate())
                        .toDate(bookTransactionRequestDto.getToDate())
                        .rentStatus(bookTransactionRequestDto.getRentStatus())
                        .activeClosed(bookTransactionRequestDto.isActiveClosed())
                        .build();

                if (bookTransactionRequestDto.getRentStatus().equals(RENT_TYPE.RENT)) {
                    BookTransaction savedBookTransaction = bookTransactionRepository.save(bookTransaction);

                    BookRequestDto updatedBookRequestDto = BookRequestDto.builder()
                            .stockCount(existBook.getStockCount() - 1)
                            .build();
                    updatedBookRequestDto.setCategoryId(existBook.getCategoryId().getId());
                    bookService.updateBooking(existBook.getId(), updatedBookRequestDto);
                    return savedBookTransaction;
                } else if (bookTransactionRequestDto.getRentStatus().equals(RENT_TYPE.RETURN)) {

                    updateTransaction(bookTransactionRequestDto);

                    BookRequestDto updatedBookRequestDto = BookRequestDto.builder()
                            .stockCount(existBook.getStockCount() + 1)
                            .build();

                    updatedBookRequestDto.setCategoryId(existBook.getCategoryId().getId());
                    Book updatedBook = bookService.updateBooking(existBook.getId(), updatedBookRequestDto);

                    return BookTransaction.builder().bookId(updatedBook).build();
                } else {
                    throw new RuntimeException("Unknown rent status");
                }
            }


        }
        throw new RuntimeException("Book is not available for rent");
    }

    @Override
    public void updateTransaction(BookTransactionRequestDto bookTransactionRequestDto) {
        BookTransaction bookTransaction = bookTransactionRepository.findById(bookTransactionRequestDto.getBookId()).orElse(null);
        if (bookTransaction != null) {
            bookTransaction.setRentStatus(RENT_TYPE.RETURN);
            bookTransaction.setActiveClosed(false);
            bookTransactionRepository.save(bookTransaction);
        }

    }



}
