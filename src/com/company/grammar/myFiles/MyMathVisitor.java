package com.company.grammar.myFiles;

import com.company.grammar.generated.MathEscapeBaseVisitor;
import com.company.grammar.generated.MathEscapeParser;

public class MyMathVisitor extends MathEscapeBaseVisitor<Node> {
    @Override
    public Node visitTerm(MathEscapeParser.TermContext ctx) {

        if (ctx.INT() != null) {
            return new IntNode(Integer.parseInt(ctx.INT().getText()));
        } else {
            return new IdNode(ctx.ID().getText());
        }

    }

    @Override
    public Node visitExpr(MathEscapeParser.ExprContext ctx) {

        if (ctx.term() != null && ctx.expr() == null) {
            return visit(ctx.term());
        }
        Node left = visit(ctx.expr());
        Node right = visit(ctx.term());
        String op;
        if (ctx.PLUS() != null){
             op = "+";
        }
        else if (ctx.MINUS() != null){
             op = "-";
        }
        else if (ctx.MULTI() != null){
             op = "*";
        }
        else if (ctx.DIV() != null){
             op = "/";
        }
        else {
            throw new RuntimeException("This operation is not allowed");
        }

        return new BinaryOpNode(left,op,right);
    }

    @Override
    public Node visitMathEscape(MathEscapeParser.MathEscapeContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new BinaryOpNode(left, "=", right);
    }
}
