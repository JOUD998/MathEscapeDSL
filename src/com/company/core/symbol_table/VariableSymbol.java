package com.company.core.symbol_table;

import com.company.core.model.ASTNode;
import com.company.core.semantic.unit.Dimension;

public class VariableSymbol extends Symbol {

    private Dimension dimension;
    private ASTNode expression;
    private double value;

    public VariableSymbol(String name, Dimension dimension, ASTNode expression) {
        super(name);
        this.dimension = dimension;
        this.expression = expression;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public ASTNode getExpression() {
        return expression;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}