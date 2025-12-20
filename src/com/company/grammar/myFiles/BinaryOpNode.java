package com.company.grammar.myFiles;

public class BinaryOpNode extends Node{
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
        return "{ \"type\": \"BinaryOp\", " +
                "\"op\": \"" + op + "\", " +
                "\"left\": " + left.toJson() + ", " +
                "\"right\": " + right.toJson() + " }";
    }
}
