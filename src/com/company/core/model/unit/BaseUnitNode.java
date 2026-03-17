package com.company.core.model.unit;

import com.company.core.model.ASTNode;
import com.company.core.semantic.ASTVisitor;

public class BaseUnitNode extends ASTNode {

    public String symbol;
    public double toBaseFactor;  // للتحويل للوحدة الأساسية

    public BaseUnitNode(String symbol){
        this.symbol = symbol;
        this.toBaseFactor = 1.0;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseUnitNode other = (BaseUnitNode) obj;
        return this.symbol != null && this.symbol.equals(other.symbol);
    }


    @Override
    public String toJson() {
        return "{\n" +
                "  \"type\": \"BaseUnitNode\",\n" +
                "  \"Symbol\": \"" + symbol + "\"\n" +
                "}";
    }
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBaseUnitNode(this);
    }


}
