package io.ffroliva.validation.excel.transformation;

import io.ffroliva.validation.excel.util.CellUtilTest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class IntegerColumnHeadersTest {

    @Test
    public void IntegerColumnHeaders_valid() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        Row row = sheet.createRow(0);
        CellUtilTest.createBlankCell(row, 0);
        CellUtilTest.createNumericCell(row, 1, 1D);
        CellUtilTest.createNumericCell(row, 2, 2D);
        CellUtilTest.createNumericCell(row, 3, 3D);

        final IntegerColumnHeaders integerColumnHeaders = new IntegerColumnHeaders(sheet, "FieldName");
        Assertions.assertThat(integerColumnHeaders.isValid()).isTrue();
        Assertions.assertThat(integerColumnHeaders.getHeaders().value()).hasSize(3);
    }

    @Test
    public void IntegerColumnHeaders_CellNotNumericShouldHaveValidationConstraints() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        Row row = sheet.createRow(0);
        CellUtilTest.createBlankCell(row, 0);
        CellUtilTest.createStringCell(row, 1, "1");
        CellUtilTest.createNumericCell(row, 2, 2D);
        CellUtilTest.createNumericCell(row, 3, 3D);

        final IntegerColumnHeaders integerColumnHeaders = new IntegerColumnHeaders(sheet, "FieldName");
        Assertions.assertThat(integerColumnHeaders.isValid()).isFalse();
        Assertions.assertThatThrownBy(() -> integerColumnHeaders.getHeaders().value()).isInstanceOf(NoSuchElementException.class);
        Assertions.assertThat(integerColumnHeaders.getHeaders().isValid()).isFalse();
        Assertions.assertThat(integerColumnHeaders.getHeaders().errors().size()).isEqualTo(1);
        Assertions.assertThat(integerColumnHeaders.getHeaders().errors().get(0).message()).isEqualTo("Cell address \"B1\" from sheet \"Test\" must be of type \"NUMERIC\" but is type \"STRING\".");
    }

    @Test
    public void IntegerColumnHeaders_singleArgumentConstructor() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        Row row = sheet.createRow(0);
        CellUtilTest.createBlankCell(row, 0);
        CellUtilTest.createNumericCell(row, 1, 3D);
        CellUtilTest.createNumericCell(row, 2, 2D);
        CellUtilTest.createNumericCell(row, 3, 1D);

        final IntegerColumnHeaders integerColumnHeaders = new IntegerColumnHeaders(sheet);
        Assertions.assertThat(integerColumnHeaders.isValid()).isFalse();
        Assertions.assertThat(integerColumnHeaders.getHeaders().errors().size()).isEqualTo(1);
        Assertions.assertThat(integerColumnHeaders.getHeaders().errors().get(0).message()).isEqualTo("\"Column headers\" from sheet \"Test\" must be in ascending order.");
    }

    @Test
    public void IntegerColumnHeaders_ConstrutorWithFieldNameOrderValidationError() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        Row row = sheet.createRow(0);
        CellUtilTest.createBlankCell(row, 0);
        CellUtilTest.createNumericCell(row, 1, 3D);
        CellUtilTest.createNumericCell(row, 2, 2D);
        CellUtilTest.createNumericCell(row, 3, 1D);

        final IntegerColumnHeaders integerColumnHeaders = new IntegerColumnHeaders(sheet, "My field name");
        Assertions.assertThat(integerColumnHeaders.isValid()).isFalse();
        Assertions.assertThat(integerColumnHeaders.getHeaders().errors().size()).isEqualTo(1);
        Assertions.assertThat(integerColumnHeaders.getHeaders().errors().get(0).message()).isEqualTo("\"My field name\" from sheet \"Test\" must be in ascending order.");
    }

}
