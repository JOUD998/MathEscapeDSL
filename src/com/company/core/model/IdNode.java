package com.company.core.model;

public class IdNode extends ASTNode {
    public String name;
    public IdNode(String name){this.name = name;}

    @Override
    public String toJson() {
        return "{ \"type\": \"Id\", \"name\": \"" + name + "\" }";
    }
}
