package com.company.core.model.unit;

import com.company.core.model.ASTNode;

public class UnitNode extends ASTNode {

    public BaseUnitNode left;
    public BaseUnitNode right;

    public UnitNode(BaseUnitNode left){
        this.left = left;
        this.right = null;
    }
    public UnitNode(BaseUnitNode left, BaseUnitNode right){
        this.left = left;
        this.right = right;
    }

    public boolean isDivision() {
        return right != null;
    }

    @Override
    public String toJson() {
        if (right == null) {
            return "{\n" +
                    "  \"type\": \"Unit\",\n" +
                    "  \"value\": " + left.toJson() + "\n" +
                    "}";
        }

        return "{\n" +
                "  \"type\": \"Unit\",\n" +
                "  \"left\": " + left.toJson() + ",\n" +
                "  \"op\": \"/\",\n" +
                "  \"right\": " + right.toJson() + "\n" +
                "}";
    }
}
