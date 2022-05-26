package io.ffroliva.validation.excel.util;

import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validated;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public class ConstraintViolationsUtil {

    public static ConstraintViolations combineConstraintViolations(Validated<?>... validated) {
        return ConstraintViolations.of(Stream.of(validated)
                .map(ConstraintViolationsUtil::getErrorsIfInvalid)
                .map(ConstraintViolations::violations)
                .flatMap(Collection::stream)
                .toList());
    }


    public static ConstraintViolations getErrorsIfInvalid(Validated<?> validated) {
        return validated.isValid() ? ConstraintViolations.of(Collections.emptyList()) : validated.errors();
    }

}
