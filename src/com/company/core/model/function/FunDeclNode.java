package com.company.core.model.function;

import com.company.core.model.ASTNode;
import com.company.core.model.IdNode;
import com.company.core.model.statment.StatementNode;
import com.company.core.model.unit.UnitNode;

import java.util.ArrayList;
import java.util.List;

public class FunDeclNode extends StatementNode implements FunctionNode {

    public IdNode funcId;
    public List<ParamNode> params = new ArrayList<>();
    public UnitNode returnType;
    public ASTNode body;



    public FunDeclNode(IdNode funcId, List<ParamNode> params, UnitNode returnType, ASTNode body){
        this.funcId = funcId;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    @Override
    public IdNode getName() {
        return funcId;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();

        sb.append("{ ");
        sb.append("\"type\": \"FunDecl\", ");
        sb.append("\"name\": \"").append(funcId.name).append("\", ");

        // params
        sb.append("\"params\": [");
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).toJson());
            if (i < params.size() - 1) sb.append(", ");
        }
        sb.append("], ");

        // return type
        sb.append("\"returnType\": ");
        if (returnType != null) {
            sb.append(returnType.toJson());
        } else {
            sb.append("null");
        }
        sb.append(", ");

        // body
        sb.append("\"body\": ");
        if (body != null) {
            sb.append(body.toJson());
        } else {
            sb.append("null");
        }

        sb.append(" }");

        return sb.toString();
    }


}
