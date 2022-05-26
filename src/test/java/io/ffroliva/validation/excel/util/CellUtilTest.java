package io.ffroliva.validation.excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class CellUtilTest {

    public static Cell createBlankCell(Row row, int columnIndex) {
        return row.createCell(columnIndex, CellType.BLANK);
    }

    public static Cell createStringCell(Row row, int columnIndex, String value) {
        Cell cell = row.createCell(columnIndex, CellType.STRING);
        cell.setCellValue(value);
        return cell;
    }

    public static Cell createNumericCell(Row row, int columnIndex, double value) {
        Cell cell = row.createCell(columnIndex, CellType.NUMERIC);
        cell.setCellValue(value);
        return cell;
    }
}
