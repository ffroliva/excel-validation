package io.ffroliva.validation.excel.model;

import org.ejml.simple.SimpleMatrix;

public class Matrix {

    private final SimpleMatrix statistics;

    public Matrix(double[][] data) {
        this.statistics = new SimpleMatrix(data);
    }

    public SimpleMatrix getStatistics() {
        return statistics.copy();
    }

}
