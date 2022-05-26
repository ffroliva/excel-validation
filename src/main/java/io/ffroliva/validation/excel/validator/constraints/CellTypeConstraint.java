package io.ffroliva.validation.excel.validator.constraints;

import am.ik.yavi.core.CustomConstraint;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class CellTypeConstraint implements CustomConstraint<Cell> {

    private final CellType cellType;

    public CellTypeConstraint(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public boolean test(Cell cell) {
        // Delegate processing to another method
        return cell.getCellType().equals(cellType);

    }

    @Override
    public Object[] arguments(Cell cell) {
        return new Object[]{
                cell.getAddress().formatAsString() /* {1} */,
                cell.getSheet().getSheetName() /* {2} */,
                cellType.toString() /* {3} */,
                cell.getCellType() /* {4} */
        };
    }

    @Override
    public String messageKey() {
        return "cell";
    }

    @Override
    public String defaultMessageFormat() {
        return "Cell address \"{1}\" from sheet \"{2}\" must be of type \"{3}\" but is type \"{4}\".";
    }
}
