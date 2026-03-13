package com.company.core.model.unit;

import com.company.core.model.ASTNode;
import com.company.core.semantic.ASTVisitor;

public class UnitNode extends ASTNode {

    public BaseUnitNode left;
    public BaseUnitNode right;

    public double toBaseFactor;

    public UnitNode(BaseUnitNode left){
        this.left = left;
        this.right = null;
        this.toBaseFactor = 1.0;
    }

    public UnitNode(BaseUnitNode left, BaseUnitNode right){
        this.left = left;
        this.right = right;
        this.toBaseFactor = 1.0;
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
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitUnitNode(this);
    }
}
