package io.ffroliva.validation.excel.validator.constraints;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderedListConstraintTest {

    public static final Validator<List<Double>> ORDERED_LIST_VALIDATOR = ValidatorBuilder.<List<Double>>of()
            .constraintOnTarget(new OrderedListConstraint("SheetName"), "column header").build();

    @Test
    public void testOrderListConstraintValid() {
        final ConstraintViolations orderedListValidator = ORDERED_LIST_VALIDATOR.validate(List.of(1D, 2D, 3D));
        Assertions.assertThat(orderedListValidator.isValid()).isTrue();
    }

    @Test
    public void testOrderListConstraintInvalid() {
        final ConstraintViolations orderedListValidator = ORDERED_LIST_VALIDATOR.validate(List.of(2D, 1D, 3D));
        Assertions.assertThat(orderedListValidator.isValid()).isFalse();
        Assertions.assertThat(orderedListValidator.size()).isEqualTo(1);
        Assertions.assertThat(orderedListValidator.get(0).message()).isEqualTo("\"column header\" from sheet \"SheetName\" must be in ascending order.");
    }
}
