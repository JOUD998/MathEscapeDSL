package com.company.core.model;

public class FactorialNode extends Node {

    public Node child;
    FactorialNode(Node child) {this.child = child;}

    @Override
    public String toJson() {
        return "{ \"type\": \"Factorial\", \"child\": " + child.toJson() +  " } Ich muss heir was andern ";
    }
}
