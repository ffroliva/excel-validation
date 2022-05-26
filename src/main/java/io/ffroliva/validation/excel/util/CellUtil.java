package io.ffroliva.validation.excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CellUtil {

    public static Stream<Cell> getCellsFromRow(Sheet sheet, int rowIndex) {
        return IntStream.range(0, sheet.getRow(rowIndex).getLastCellNum())
                .mapToObj(columnIndex -> sheet.getRow(rowIndex).getCell(columnIndex));
    }

    public static Stream<Cell> getCellsFromFirstColumn(Sheet sheet) {
        return IntStream.rangeClosed(0, sheet.getLastRowNum())
                .mapToObj(rowIndex -> sheet.getRow(rowIndex).getCell(0));
    }


}
