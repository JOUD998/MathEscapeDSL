package com.company.core.model.function;

import com.company.core.model.ASTNode;

import java.util.List;

public class ParamListNode extends ASTNode {

    public List<ParamNode> paramNodeList;
    public ParamListNode(List<ParamNode> paramNodeList) {
        this.paramNodeList = paramNodeList;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"type\": \"ParamList\", \"params\": [");

        for (int i = 0; i < paramNodeList.size(); i++) {
            sb.append(paramNodeList.get(i).toJson());
            if (i < paramNodeList.size() - 1) sb.append(", ");
        }

        sb.append("] }");
        return sb.toString();
    }
}
