package com.company.core.model;

import com.company.core.model.statment.StatementNode;
import com.company.core.model.unit.UnitNode;

public class VariableNode extends StatementNode {

    public IdNode varId;
    public UnitNode unit;
    public ASTNode expression;

    public VariableNode(IdNode varId, UnitNode unit, ASTNode expression) {
        this.varId = varId;
        this.unit = unit;
        this.expression = expression;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();

        sb.append("{ ");
        sb.append("\"type\": \"VariableDecl\", ");
        sb.append("\"name\": \"").append(varId.name).append("\", ");

        // unit
        sb.append("\"unit\": ");
        if (unit != null) {
            sb.append(unit.toJson());
        } else {
            sb.append("null");
        }
        sb.append(", ");

        // expression
        sb.append("\"expression\": ");
        if (expression != null) {
            sb.append(expression.toJson());
        } else {
            sb.append("null");
        }

        sb.append(" }");

        return sb.toString();
    }
}
