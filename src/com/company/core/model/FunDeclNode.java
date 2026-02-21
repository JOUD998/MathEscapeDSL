package com.company.core.model;

import java.util.ArrayList;
import java.util.List;

public class FunDeclNode extends ASTNode{

    public IdNode idNode;
    public List<ParamNode> params = new ArrayList<>();

    public FunDeclNode(IdNode idNode){
        this.idNode = idNode;
    }
    public FunDeclNode(IdNode idNode,List<ParamNode> params){
        this.idNode = idNode;
        this.params = params;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"type\": \"FunDecl\", ");
        sb.append("\"name\": \"").append(idNode.name).append("\", ");
        sb.append("\"params\": [");
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).toJson());
            if (i < params.size() - 1) sb.append(",");
        }
        sb.append("] }");  // حذفت returnType
        return sb.toString();
    }
}
