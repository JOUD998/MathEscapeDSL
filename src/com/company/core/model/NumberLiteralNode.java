package com.company.core.model;

import com.company.core.model.unit.UnitNode;

public class NumberLiteralNode extends ASTNode {

    double value;
    UnitNode unitNode;
    public NumberLiteralNode(double value, UnitNode unitNode){
        this.value = value;
        this.unitNode = unitNode;
    }


    @Override
    public String toJson() {

        if (unitNode == null) {
            return "{\n" +
                    "  \"type\": \"NumberLiteral\",\n" +
                    "  \"value\": " + value + "\n" +
                    "}";
        }

        return "{\n" +
                "  \"type\": \"NumberLiteral\",\n" +
                "  \"value\": " + value + ",\n" +
                "  \"UnitNode\": " + unitNode.toJson() + "\n" +
                "}";
    }
}
