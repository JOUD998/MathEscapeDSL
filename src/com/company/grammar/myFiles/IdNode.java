package com.company.grammar.myFiles;

public class IdNode extends Node{
    String id;
    IdNode(String id){this.id = id;}

    @Override
    public String toJson() {
        return "{ \"type\": \"Id\", \"name\": \"" + id + "\" }";
    }
}
