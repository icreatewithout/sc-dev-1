package com.ifeb2.scdevbase.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExportUtils {


    public static void export(List<Map<String, Object>> list, String[] title, String sheetName) throws IOException {

        OutputStream out = null;
        HSSFWorkbook workbook = null;


        workbook = new HSSFWorkbook();

        int sheetSize = 65536;

        // 取出一共有多少个sheet.
        double sheetNo = Math.ceil(list.size() / sheetSize);

        for (int index = 0; index <= sheetNo; index++) {
            // 产生工作表对象
            HSSFSheet sheet = workbook.createSheet();
            if (sheetNo == 0) {
                workbook.setSheetName(index, sheetName);
            } else {
                // 设置工作表的名称.
                workbook.setSheetName(index, sheetName + index);
            }
            HSSFRow row;
            HSSFCell cell; // 产生单元格

            row = sheet.createRow(0);

            // 写入各个字段的列头名称
            for (int i = 0; i < title.length; i++) {

                cell = row.createCell(i);
                // 设置列中写入内容为String类型
                cell.setCellType(CellType.STRING);
                // 单元格样式
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                HSSFFont font = workbook.createFont();
                // 粗体显示
                font.setBold(true);
                // 选择需要用到的字体格式
                cellStyle.setFont(font);
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
                // 设置列宽
                sheet.setColumnWidth(i, 3766);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);

                // 写入列名
                cell.setCellValue(title[i]);
            }

            int startNo = index * sheetSize;
            int endNo = Math.min(startNo + sheetSize, list.size());
            // 写入各条记录,每条记录对应excel表中的一行
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setAlignment(HorizontalAlignment.CENTER);
            cs.setVerticalAlignment(VerticalAlignment.CENTER);
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                Map<String, Object> map = list.get(i);
                for (int j = 0; j < list.get(i).size(); j++) {
                    cell = row.createCell(j);
                    cell.setCellStyle(cs);
                    cell.setCellType(CellType.STRING);

                    if (map.get(String.valueOf(j)) != null && !map.get(String.valueOf(j)).toString().equals("-")) {
                        cell.setCellValue(map.get(String.valueOf(j)).toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(filename);
            workbook.write(out);
            out.close();
        }

    }

    /**
     * 编码文件名
     */
    private static String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + filename + ".xls";
        return filename;
    }

}
