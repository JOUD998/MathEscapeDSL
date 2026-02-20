package com.company.core.model;

public class PowerNode extends ASTNode {
    public ASTNode base;
    public ASTNode exponent;

    public PowerNode(ASTNode base, ASTNode exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public String toJson() {
        return "{ \"type\": \"Power\", \"base\": " + base.toJson() + ", \"exponent\": " + exponent.toJson() + " }";
    }
}