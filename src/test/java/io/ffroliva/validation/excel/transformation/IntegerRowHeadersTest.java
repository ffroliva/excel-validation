package io.ffroliva.validation.excel.transformation;

import io.ffroliva.validation.excel.util.CellUtilTest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class IntegerRowHeadersTest {

    @Test
    public void IntegerColumnHeaders_valid() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        Row row1 = sheet.createRow(0);
        Row row2 = sheet.createRow(1);
        Row row3 = sheet.createRow(2);
        Row row4 = sheet.createRow(3);
        CellUtilTest.createBlankCell(row1, 0);
        CellUtilTest.createNumericCell(row2, 0, 1D);
        CellUtilTest.createNumericCell(row3, 0, 2D);
        CellUtilTest.createNumericCell(row4, 0, 3D);

        final IntegerRowHeaders integerRowHeaders = new IntegerRowHeaders(sheet , "FieldName");
        Assertions.assertThat(integerRowHeaders.isValid()).isTrue();
        Assertions.assertThat(integerRowHeaders.getHeaders().value()).hasSize(3);
    }

    @Test
    public void IntegerColumnHeaders_invalid_NotNumericCell() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        Row row1 = sheet.createRow(0);
        Row row2 = sheet.createRow(1);
        Row row3 = sheet.createRow(2);
        Row row4 = sheet.createRow(3);
        CellUtilTest.createBlankCell(row1, 0);
        CellUtilTest.createStringCell(row2, 0, "1"); // Cell Address A2
        CellUtilTest.createNumericCell(row3, 0, 2D); // Cell Address A3
        CellUtilTest.createNumericCell(row4, 0, 3D);

        final IntegerRowHeaders integerRowHeaders = new IntegerRowHeaders(sheet);
        Assertions.assertThat(integerRowHeaders.isValid()).isFalse();
        Assertions.assertThatThrownBy(() -> integerRowHeaders.getHeaders().value()).isInstanceOf(NoSuchElementException.class);
        Assertions.assertThat(integerRowHeaders.getHeaders().isValid()).isFalse();
        Assertions.assertThat(integerRowHeaders.getHeaders().errors().size()).isEqualTo(1);
        Assertions.assertThat(integerRowHeaders.getHeaders().errors().get(0).message()).isEqualTo("Cell address \"A2\" from sheet \"Test\" must be of type \"NUMERIC\" but is type \"STRING\".");
    }


}
