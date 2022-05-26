package io.ffroliva.validation.excel.validator.constraints;

import am.ik.yavi.core.CustomConstraint;
import com.google.common.collect.Ordering;
import io.ffroliva.validation.excel.validator.ExcelValidationMessages;

import java.util.List;

public class OrderedListConstraint implements CustomConstraint<List<Double>> {

    private final String sheetName;

    public OrderedListConstraint(String sheetName) {
        this.sheetName = sheetName;
    }

    @Override
    public Object[] arguments(List<Double> list) {
        return new Object[]{
                /* {0} is reserved for the field name */
                sheetName /* {1} */,
                /* {2} is reserved for the validated value */
        };
    }

    @Override
    public boolean test(List<Double> list) {
        // Delegate processing to another method
        return Ordering.natural().isStrictlyOrdered(list);
    }

    @Override
    public String messageKey() {
        return ExcelValidationMessages.UNORDERED_LIST_FROM_SHEET.messageKey();
    }

    @Override
    public String defaultMessageFormat() {
        return ExcelValidationMessages.UNORDERED_LIST_FROM_SHEET.defaultMessageFormat();
    }
}
