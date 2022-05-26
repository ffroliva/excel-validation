package io.ffroliva.validation.excel.model;

import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.Validated;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ffroliva.validation.excel.transformation.excel.MatrixSheet;
import io.ffroliva.validation.excel.util.CellUtilTest;
import io.ffroliva.validation.excel.util.TestUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MatrixSheetTest {

    @Test
    public void testReadData() throws IOException {
        Sheet sheet = TestUtils.getSheet("excel/ExcelInput.xlsx", "Rank statistics");
        final MatrixSheet matrixSheet = new MatrixSheet(sheet);
        Assertions.assertThat(matrixSheet).isNotNull();
        Validated<Matrix> rankStatistics = matrixSheet.read();
        Assertions.assertThat(rankStatistics).isNotNull();
        Assertions.assertThat(rankStatistics.value().getStatistics()).isNotNull();
    }

    @Test
    public void testReadDataSerializeAsJson() throws IOException {
        Sheet sheet = TestUtils.getSheet("excel/ExcelInput.xlsx", "Rank statistics");
        final MatrixSheet matrixSheet = new MatrixSheet(sheet);
        Assertions.assertThat(matrixSheet).isNotNull();
        Validated<Matrix> rankStatistics = matrixSheet.read();
        ObjectMapper mapper = new ObjectMapper();
        final String jsonAsString = mapper
                //.writerWithDefaultPrettyPrinter() //enable to pretty print the json
                .writeValueAsString(rankStatistics.value());
        Assertions.assertThat(jsonAsString).isNotNull();
        System.out.println(jsonAsString);
    }

    @Test
    public void testReadDataWithColumnAndRowHeadersWithError() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        Row row = sheet.createRow(0);
        // Column Headers
        CellUtilTest.createBlankCell(row, 0);
        CellUtilTest.createStringCell(row, 1, "a");
        CellUtilTest.createNumericCell(row, 2, 2D);
        CellUtilTest.createNumericCell(row, 3, 3D);
        Row row2 = sheet.createRow(1);
        Row row3 = sheet.createRow(2);
        Row row4 = sheet.createRow(3);
        // Row Headers
        CellUtilTest.createStringCell(row2, 0, "1"); // Cell Address A2
        CellUtilTest.createNumericCell(row3, 0, 2D); // Cell Address A3
        CellUtilTest.createNumericCell(row4, 0, 3D);

        final Validated<Matrix> readRankStatisticsValidated = new MatrixSheet(sheet).read();
        Assertions.assertThat(readRankStatisticsValidated.isValid()).isFalse();
        Assertions.assertThat(readRankStatisticsValidated.errors().size()).isEqualTo(2);

        Assertions.assertThat(readRankStatisticsValidated.errors().get(0).message()).isEqualTo("Cell address \"B1\" from sheet \"Test\" must be of type \"NUMERIC\" but is type \"STRING\".");
        Assertions.assertThat(readRankStatisticsValidated.errors().get(1).message()).isEqualTo("Cell address \"A2\" from sheet \"Test\" must be of type \"NUMERIC\" but is type \"STRING\".");
    }

    @Test
    public void testReadDataOnlyHeadersNoMatrixData_Invalid() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        Row row = sheet.createRow(0);
        // Column Headers
        CellUtilTest.createBlankCell(row, 0);
        CellUtilTest.createNumericCell(row, 1, 1D);
        CellUtilTest.createNumericCell(row, 2, 2D);
        CellUtilTest.createNumericCell(row, 3, 3D);
        Row row2 = sheet.createRow(1);
        Row row3 = sheet.createRow(2);
        Row row4 = sheet.createRow(3);
        // Row Headers
        CellUtilTest.createNumericCell(row2, 0, 1D); // Cell Address A2
        CellUtilTest.createNumericCell(row3, 0, 2D); // Cell Address A3
        CellUtilTest.createNumericCell(row4, 0, 3D);

        final Validated<Matrix> readRankStatisticsValidated = new MatrixSheet(sheet).read();
        Assertions.assertThat(readRankStatisticsValidated.isValid()).isFalse();
        readRankStatisticsValidated.errors().stream()
                .map(ConstraintViolation::message)
                .map(message -> {
                    System.out.println(message);
                    return message;
                }).toList();

    }
}
