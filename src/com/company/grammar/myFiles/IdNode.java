package com.company.grammar.myFiles;

public class IdNode extends Node{
    String name;
    IdNode(String name){this.name = name;}

    @Override
    public String toJson() {
        return "{ \"type\": \"Id\", \"name\": \"" + name + "\" }";
    }
}
