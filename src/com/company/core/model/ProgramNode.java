package com.company.core.model;

import com.company.core.model.statment.StatementNode;

import java.util.List;

public class ProgramNode extends ASTNode{

    public List<ASTNode> statements;
    public ProgramNode(List<ASTNode> statements){
        this.statements = statements;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("\"type\": \"Program\", ");
        sb.append("\"statements\": [");

        for (int i = 0; i < statements.size(); i++) {
            sb.append(statements.get(i).toJson());
            if (i < statements.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("] ");
        sb.append("}");
        return sb.toString();
    }
}
