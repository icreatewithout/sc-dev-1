package com.ifeb2.scdevbase.utils;

import com.ifeb2.scdevbase.exception.ExcelImportException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportUtils {

    public static List<Map<String, Object>> imports(InputStream inputStream) throws IOException, InvalidFormatException, ExcelImportException {

        Sheet sheet = null;
        Workbook workbook = WorkbookFactory.create(inputStream);
        sheet = workbook.getSheetAt(0);

        if (sheet == null) {
            throw new ExcelImportException("sheet名称异常！");
        }

        List<Map<String, Object>> list = getCell(sheet);
        inputStream.close();
        return list;
    }

    private static List<Map<String, Object>> getCell(Sheet sheet) {
        List<Map<String, Object>> maps = new ArrayList<>();

        int rows = sheet.getPhysicalNumberOfRows();

        if (rows > 0) {
            int column = 0;
            for (int i = 1; i < rows; i++) {

                Row row = sheet.getRow(i);

                if (column < 1) {
                    column = row.getLastCellNum();
                }

                Map<String, Object> map = new HashMap<>();
                for (int j = 0; j < column; j++) {
                    if (row.getCell(j) != null) {
                        row.getCell(j).setCellType(CellType.STRING);
                    }
                    map.put(String.valueOf(j), row.getCell(j) == null ? null : row.getCell(j).getStringCellValue());
                }
                maps.add(map);
            }

        }

        return maps;
    }

}
