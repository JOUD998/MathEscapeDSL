package com.company.core.symbol_table;

import java.util.List;

import com.company.core.model.unit.UnitNode;
import com.company.core.semantic.unit.Dimension;

public class FunctionSymbol extends Symbol {

    private List<VariableSymbol> parameters;
    private Dimension dimension;

    public FunctionSymbol(String name, List<VariableSymbol> parameters, Dimension dimension) {
        super(name);
        this.parameters = parameters;
        this.dimension = dimension;
    }

    public List<VariableSymbol> getParameters() {
        return parameters;
    }

    public Dimension getDimension() {
        return dimension;
    }
}