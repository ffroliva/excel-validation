package io.ffroliva.validation.excel.transformation.excel;

import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validated;
import io.ffroliva.validation.excel.model.Matrix;
import io.ffroliva.validation.excel.transformation.IntegerColumnHeaders;
import io.ffroliva.validation.excel.transformation.IntegerRowHeaders;
import io.ffroliva.validation.excel.util.ConstraintViolationsUtil;
import io.ffroliva.validation.excel.validator.CellValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;

import static am.ik.yavi.fn.Validation.failure;
import static am.ik.yavi.fn.Validation.success;

/**
 * Rank statistics.
 * <p>
 * Example:
 * 1	    2	    3 	    4	    5	    6
 * 1	10.55	13.31	15.82	18.19	20.73	23.11
 * 2	23.08	27.96	32.51	37.07	41.57	46.1
 * 3	39.34	46	    52.71	59.39	65.9	72.33
 * 4	59.16	68.04	76.68	85.59	93.98	102.5
 * 5	82.88	93.13	103.7	114.7	125.1	135.8
 * 6	110.6	123.3	136.2	148.4	160.9	173
 * 7	141.8	157	    171.6	185.9	199.9	214
 * 8	176.9	192.8	209.1	225.5	241.7	257.5
 * 9	215.9	235.1	253.2	271.2	289.5	306.9
 * 10	258	    278.9	298.9	319.2	338.7	358.9
 * 11	305.8	327.1	349.2	371	    392.4	414.1
 * 12	355.9	379.8	403.3	426.8	450.3	473.6
 *
 * @author Flavio Oliva
 */
public class MatrixSheet {
    private final Sheet sheet;

    public MatrixSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public Validated<Matrix> read() {
        final IntegerColumnHeaders integerColumnHeaders = new IntegerColumnHeaders(sheet);
        final IntegerRowHeaders integerRowHeaders = new IntegerRowHeaders(sheet);
        if (!integerColumnHeaders.isValid() || !integerRowHeaders.isValid()) {
            final ConstraintViolations constraintViolations = ConstraintViolationsUtil.combineConstraintViolations(
                    integerColumnHeaders.getHeaders(),
                    integerRowHeaders.getHeaders());
            return Validated.of(Validated.failure(constraintViolations));
        }
        short numOfColumns = sheet.getRow(0).getLastCellNum();
        int numOfRows = sheet.getLastRowNum();
        final double[][] data = new double[numOfRows][numOfColumns];
        ConstraintViolations constraintViolations = new ConstraintViolations();
        for (int rIndex = 1; numOfRows > rIndex; rIndex++) {
            for (int cIndex = 1; numOfColumns > cIndex; cIndex++) {
                Cell cell = CellUtil.getCell(sheet.getRow(rIndex), cIndex); //CellUtil#getCell creates a cell it it doesn't exist
                final Validated<Double> cellValidated = CellValidator.NUMERIC_CELL_TYPE_VALIDATOR.validate(cell);
                if (cellValidated.isValid()) {
                    data[rIndex - 1][cIndex - 1] = sheet.getRow(rIndex).getCell(cIndex).getNumericCellValue();
                } else {
                    constraintViolations.addAll(cellValidated.errors());
                }
            }
        }
        if (constraintViolations.isValid()) {
            return Validated.of(success(new Matrix(data)));
        } else {
            return Validated.of(failure(constraintViolations));
        }
    }

}
