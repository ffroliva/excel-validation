package io.ffroliva.validation.excel.transformation;

import am.ik.yavi.core.Validated;
import io.ffroliva.validation.excel.util.CellUtil;
import io.ffroliva.validation.excel.validator.CellValidator;
import io.ffroliva.validation.excel.validator.OrderListValidator;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

@Getter
public class IntegerColumnHeaders {

    public static final String DEFAULT_COLUMN_HEADERS = "Column headers";

    private final Sheet sheet;
    private final String fieldName;
    private final Validated<List<Integer>> headers;

    public IntegerColumnHeaders(Sheet sheet) {
        this(sheet, DEFAULT_COLUMN_HEADERS);
    }

    public IntegerColumnHeaders(Sheet sheet, String fieldName) {
        this.sheet = sheet;
        this.fieldName = fieldName;
        this.headers = validateNumericCellType();
    }

    private Validated<List<Integer>> validateNumericCellType() {
        return CellValidator.NUMERIC_CELL_TYPE_VALIDATOR
                .liftList()
                .andThen(OrderListValidator.orderListValidator(sheet.getSheetName(), fieldName))
                .andThen(c -> c.stream().map(Double::intValue).toList())
                .validate(getRowCellsSkippingFirst());
    }

    public boolean isValid() {
        return headers.isValid();
    }


    private List<Cell> getRowCellsSkippingFirst() {
        return CellUtil.getCellsFromRow(sheet, 0).skip(1).toList();
    }


}
