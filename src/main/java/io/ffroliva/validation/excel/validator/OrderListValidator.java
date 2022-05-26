package io.ffroliva.validation.excel.validator;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ApplicativeValidator;
import io.ffroliva.validation.excel.validator.constraints.OrderedListConstraint;

import java.util.List;

public class OrderListValidator {

    /**
     * @param sheetName the sheet name which the field name is from
     * @param fieldName the field name
     * @return an instance of ${@link am.ik.yavi.core.ApplicativeValidator}
     */
    public static ApplicativeValidator<List<Double>> orderListValidator(String sheetName, String fieldName) {
        return ValidatorBuilder.<List<Double>>of()
                .constraintOnTarget(new OrderedListConstraint(sheetName), fieldName).build().applicative();
    }

}
