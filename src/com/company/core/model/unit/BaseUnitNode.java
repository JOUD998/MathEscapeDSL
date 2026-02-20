package com.company.core.model.unit;

import com.company.core.model.ASTNode;

public class BaseUnitNode extends ASTNode {

    public String symbol;
    public BaseUnitNode(String symbol){
        this.symbol=symbol;
    }

    @Override
    public String toJson() {
        return "{\n" +
                "  \"type\": \"BaseUnitNode\",\n" +
                "  \"symbol\": \"" + symbol + "\"\n" +
                "}";
    }


}
