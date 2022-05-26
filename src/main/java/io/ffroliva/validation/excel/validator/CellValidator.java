package io.ffroliva.validation.excel.validator;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ValueValidator;
import io.ffroliva.validation.excel.validator.constraints.CellTypeConstraint;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class CellValidator {

    public static final ValueValidator<Cell, String> STRING_CELL_TYPE_VALIDATOR = ValidatorBuilder.<Cell>of()
            .constraintOnTarget(new CellTypeConstraint(CellType.STRING), "cell")
            .build().applicative().andThen(Cell::getStringCellValue);

    public static final ValueValidator<Cell, Double> NUMERIC_CELL_TYPE_VALIDATOR = ValidatorBuilder.<Cell>of()
            .constraintOnTarget(new CellTypeConstraint(CellType.NUMERIC), "cell")
            .build().applicative().andThen(Cell::getNumericCellValue);


}
