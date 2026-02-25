package com.company.core.model;

public class TermNode extends ASTNode{

    public ASTNode term;
    public char op;
    public ASTNode factor;


    public TermNode(ASTNode term, char op, ASTNode factor){
        this.term = term;
        this.op=op;
        this.factor=factor;

    }

    @Override
    public String toJson() {
        return "{ \"type\": \"Term\", \"op\": \"" + op + "\", " +
                "\"term\": " + (term != null ? term.toJson() : "null") + ", " +
                "\"factor\": " + (factor != null ? factor.toJson() : "null") + " }";
    }
}
