package io.ffroliva.validation.excel.validator;

import am.ik.yavi.core.Validated;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellValidatorTest {


    @Test
    public void testNullCellValidation() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        final Validated<String> validate = CellValidator.STRING_CELL_TYPE_VALIDATOR.validate(cell);
        Assertions.assertThat(validate.isValid()).isFalse();
        Assertions.assertThat(validate.errors().size()).isEqualTo(1);
        Assertions.assertThat(validate.errors().get(0).message())
                .isEqualTo("Cell address \"A1\" from sheet \"Sheet0\" must be of type \"STRING\" but is type \"BLANK\".");
    }


    @Test
    public void testBlankCellTypeExpectedToBeCellTypeString() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0, CellType.BLANK);

        final Validated<String> validate = CellValidator.STRING_CELL_TYPE_VALIDATOR.validate(cell);
        Assertions.assertThat(validate.isValid()).isFalse();
        Assertions.assertThat(validate.errors().size()).isEqualTo(1);
        Assertions.assertThat(validate.errors().get(0).message())
                .isEqualTo("Cell address \"A1\" from sheet \"Sheet0\" must be of type \"STRING\" but is type \"BLANK\".");
    }

    @Test
    public void testStringCellTypeExpectedToBeString() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0, CellType.STRING);

        final Validated<String> validate = CellValidator.STRING_CELL_TYPE_VALIDATOR.validate(cell);
        Assertions.assertThat(validate.isValid()).isTrue();
        Assertions.assertThat(validate.value()).isEqualTo("");
    }

    @Test
    public void testNumericCellTypeExpectedToBeNumeric() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0, CellType.NUMERIC);

        final Validated<Double> validate = CellValidator.NUMERIC_CELL_TYPE_VALIDATOR.validate(cell);
        Assertions.assertThat(validate.isValid()).isTrue();
        Assertions.assertThat(validate.value()).isEqualTo(0D);
    }

    @Test
    public void testCellTypeExpectedToBeNumeric() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0, CellType.STRING);

        final Validated<Double> validate = CellValidator.NUMERIC_CELL_TYPE_VALIDATOR.validate(cell);
        Assertions.assertThat(validate.isValid()).isFalse();
        Assertions.assertThat(validate.errors().size()).isEqualTo(1);
        Assertions.assertThat(validate.errors().get(0).message())
                .isEqualTo("Cell address \"A1\" from sheet \"Sheet0\" must be of type \"NUMERIC\" but is type \"STRING\".");
    }


}
