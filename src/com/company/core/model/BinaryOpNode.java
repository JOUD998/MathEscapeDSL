package com.company.core.model;

public class BinaryOpNode extends ASTNode {
    public ASTNode left;
    public String op;
    public ASTNode right;

    public BinaryOpNode(ASTNode left, String op, ASTNode right){
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
