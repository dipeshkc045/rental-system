package com.bookrental.api.excel.helper;

import com.bookrental.api.excel.model.request.BookTransactionRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
public class Helper {

    private static final String[] HEADERS = {
            "id", "Code",  "FromDate", "toDate", "rentStatus", "activeClosed","bookId","memberId"
    };

    private static final String SHEET_NAME = "book_transaction";

    public static ByteArrayInputStream dataToExcel(List<BookTransactionRequestDTO> list) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }


            int rowIndex = 1;
            for (BookTransactionRequestDTO bookTransaction : list) {
                Row dataRow = sheet.createRow(rowIndex++);

                dataRow.createCell(0).setCellValue(bookTransaction.getId());
                dataRow.createCell(1).setCellValue(bookTransaction.getCode());
                dataRow.createCell(2).setCellValue(bookTransaction.getFromDate().toString());
                dataRow.createCell(3).setCellValue(bookTransaction.getToDate().toString());
                dataRow.createCell(4).setCellValue(bookTransaction.getRentStatus().toString());
                dataRow.createCell(5).setCellValue(bookTransaction.isActiveClosed());
                dataRow.createCell(6).setCellValue(bookTransaction.getBookId().toString());
                dataRow.createCell(7).setCellValue(bookTransaction.getMemberId().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            log.error("Failed to export data to Excel", e);
            throw e;
        } finally {
            try {
                out.close();
                workbook.close();
            } catch (IOException e) {
                log.error("Error closing resources", e);
            }
        }
    }
}
