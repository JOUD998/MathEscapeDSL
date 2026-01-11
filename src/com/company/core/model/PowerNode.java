package com.company.core.model;

public class PowerNode extends Node {
    public Node base;
    public Node exponent;

    public PowerNode(Node base, Node exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public String toJson() {
        return "{ \"type\": \"Power\", \"base\": " + base.toJson() + ", \"exponent\": " + exponent.toJson() + " }";
    }
}