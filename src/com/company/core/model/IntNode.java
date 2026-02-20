package com.company.core.model;

public class IntNode extends ASTNode {
    public int value;

    public IntNode(int value){this.value = value;}


    @Override
    public String toJson() {
        return "{ \"type\": \"Int\", \"value\": " + value +  " } ";
    }
}
