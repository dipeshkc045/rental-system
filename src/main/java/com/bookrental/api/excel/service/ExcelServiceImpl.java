package com.bookrental.api.excel.service;

import com.bookrental.api.excel.helper.Helper;
import com.bookrental.api.excel.model.request.BookTransactionRequestDTO;
import com.bookrental.api.transaction.model.entity.BookTransaction;
import com.bookrental.api.transaction.repository.BookTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExcelServiceImpl implements ExcelService {


    private final BookTransactionRepository bookTransactionRepository;

    @Override
    public ByteArrayInputStream getActualData() throws IOException {

        List<BookTransaction> bookTransactionList = bookTransactionRepository.findAll();


        List<BookTransactionRequestDTO> bookTransactionRequestDTOList = bookTransactionList.stream()
                .map(bookTransaction -> BookTransactionRequestDTO.builder()
                        .id(bookTransaction.getId().intValue())
                        .code(bookTransaction.getCode())
                        .fromDate(bookTransaction.getFromDate())
                        .toDate(bookTransaction.getToDate())
                        .bookId(bookTransaction.getBookId().getId())
                        .memberId(bookTransaction.getMemberId().getId())
                        .activeClosed(bookTransaction.isActiveClosed())
                        .rentStatus(bookTransaction.getRentStatus())
                        .build())
                .toList();
        return Helper.dataToExcel(bookTransactionRequestDTOList);
    }

}
