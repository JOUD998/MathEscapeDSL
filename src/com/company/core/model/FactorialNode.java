package com.company.core.model;

public class FactorialNode extends ASTNode {

    public ASTNode child;
    public FactorialNode(ASTNode child) {this.child = child;}

    @Override
    public String toJson() {
        return "{ \"type\": \"Factorial\", \"child\": " + child.toJson() +  " } Ich muss heir was andern ";
    }
}
