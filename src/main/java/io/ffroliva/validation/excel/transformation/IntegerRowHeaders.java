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
public class IntegerRowHeaders {

    public static final String DEFAULT_ROW_HEADERS = "Row headers";

    private final Sheet sheet;
    private final String fieldName;
    private final Validated<List<Integer>> headers;

    public IntegerRowHeaders(Sheet sheet) {
        this(sheet, DEFAULT_ROW_HEADERS);
    }

    public IntegerRowHeaders(Sheet sheet, String fieldName) {
        this.sheet = sheet;
        this.fieldName = fieldName;
        this.headers = validateNumericCellType();
    }

    private Validated<List<Integer>> validateNumericCellType() {
        return CellValidator.NUMERIC_CELL_TYPE_VALIDATOR
                .liftList()
                .andThen(OrderListValidator.orderListValidator(sheet.getSheetName(), fieldName))
                .andThen(c -> c.stream().map(Double::intValue).toList())
                .validate(getRowHeaderCellsSkippingFirst());
    }


    public boolean isValid() {
        return headers.isValid();
    }

    private List<Cell> getRowHeaderCellsSkippingFirst() {
        return CellUtil.getCellsFromFirstColumn(sheet).skip(1).toList();
    }


}
