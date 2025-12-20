package com.company.grammar.myFiles;

public class IntNode extends Node{
    public int value;

    public IntNode(int value){this.value = value;}


    @Override
    public String toJson() {
        return "{ \"type\": \"Int\", \"value\": " + value +  " } ";
    }
}
