package io.ffroliva.validation.excel.validator;

import am.ik.yavi.core.ApplicativeValidator;
import am.ik.yavi.core.Validated;
import am.ik.yavi.core.ValueValidator;
import com.google.common.primitives.Doubles;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderValidatorTest {

    @Test
    public void orderListValidatorTestValid() {
        final ValueValidator<List<Double>, List<Double>> orderListValidator = OrderListValidator.orderListValidator("Sheet name", "field name");
        final Validated<List<Double>> validate = orderListValidator.validate(Doubles.asList(1, 2, 3));
        Assertions.assertThat(validate.isValid()).isTrue();
    }

    @Test
    public void orderListValidatorTestInvalid() {
        final ApplicativeValidator<List<Double>> orderListValidator = OrderListValidator.orderListValidator("Rank statistics", "column header");
        final Validated<List<Double>> validate = orderListValidator.validate(Doubles.asList(2, 1, 3));
        Assertions.assertThat(validate.isValid()).isFalse();
        Assertions.assertThat(validate.errors().size()).isEqualTo(1);
        Assertions.assertThat(validate.errors().get(0).message()).isEqualTo("\"column header\" from sheet \"Rank statistics\" must be in ascending order.");
    }
}
