package com.company.core.model;

public class IdNode extends Node {
    public String name;
    IdNode(String name){this.name = name;}

    @Override
    public String toJson() {
        return "{ \"type\": \"Id\", \"name\": \"" + name + "\" }";
    }
}
