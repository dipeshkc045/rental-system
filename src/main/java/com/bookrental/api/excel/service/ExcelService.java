package com.bookrental.api.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ExcelService {
    ByteArrayInputStream getActualData() throws IOException;
}
