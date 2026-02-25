package com.company.core.model;

import com.company.core.model.statment.StatementNode;

public class ExprStmtNode extends StatementNode {

    public ASTNode expr;
    public ExprStmtNode(ASTNode expr) {
        this.expr = expr;
    }

    @Override
    public String toJson() {
        return "{ \"type\": \"ExprStmt\", \"expr\": " + (expr != null ? expr.toJson() : "null") + " }";
    }
}
