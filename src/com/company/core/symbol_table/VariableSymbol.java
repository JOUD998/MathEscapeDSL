package com.company.core.symbol_table;

import com.company.core.semantic.unit.Dimension;

public class VariableSymbol extends Symbol {

    private Dimension dimension;

    public VariableSymbol(String name, Dimension dimension) {
        super(name);
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return dimension;
    }
}