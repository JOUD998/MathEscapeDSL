package com.company.core.model;

public class BinaryOpNode extends Node {
    public Node left;
    public String op;
    public Node right;

    BinaryOpNode(Node left,String op,Node right){
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public String toJson() {
        return "{\n \"type\": \"BinaryOp\",\n " +
                "\"op\": \"" + op + "\",\n " +
                "\"left\": " + left.toJson() + ",\n " +
                "\"right\": " + right.toJson() + " \n}";
    }
}
