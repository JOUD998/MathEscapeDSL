package com.company.core.interpreter;

import com.company.core.model.*;
import com.company.generated_Grammars.MathEscapeBaseVisitor;
import com.company.generated_Grammars.MathEscapeParser;

public class MyMathVisitor extends MathEscapeBaseVisitor<Node> {

    @Override
    public Node visitMathEscape(MathEscapeParser.MathEscapeContext ctx) {
        return visit(ctx.statement());
    }



    @Override
    public Node visitStatement(MathEscapeParser.StatementContext ctx) {

        Node left = new IdNode(ctx.ID().getText());
        Node right = visit(ctx.expr());
        return new BinaryOpNode(left,"=",right);

    }

    @Override
    public Node visitExpr(MathEscapeParser.ExprContext ctx) {

        if (ctx.expr() == null) {
            return visit(ctx.term());
        }

        Node left = visit(ctx.expr());
        Node right = visit(ctx.term());

        String op;
        if (ctx.PLUS() != null) {
            op = "+";
        } else if (ctx.MINUS() != null) {
            op = "-";
        } else {
            throw new RuntimeException("This operation is not allowed");
        }

        return new BinaryOpNode(left, op, right);
    }
    @Override
    public Node visitTerm(MathEscapeParser.TermContext ctx) {

        if (ctx.term() == null) {
            return visit(ctx.factor());
        }

        Node left = visit(ctx.term());
        Node right = visit(ctx.factor());

        String op;
        if (ctx.MULTI() != null) {
            op = "*";
        } else if (ctx.DIV() != null) {
            op = "/";
        } else {
            throw new RuntimeException("This operation is not allowed");
        }

        return new BinaryOpNode(left, op, right);
    }

    @Override
    public Node visitFactor(MathEscapeParser.FactorContext ctx) {
        Node base = visit(ctx.postfix());

        // إذا فيه power: postfix ^ factor
        if (ctx.factor() != null) {
            Node exponent = visit(ctx.factor());
            base = new PowerNode(base, exponent);
        }

        return base;
    }
    @Override
    public Node visitPostfix(MathEscapeParser.PostfixContext ctx) {

        Node base = visit(ctx.primary());

//        إذا فيه FACT، لفّ Node بـ FactorialNode

        if (ctx.FACT() != null) {
            base = new FactorialNode(base);
        }

        return base;



    }

    @Override
    public Node visitPrimary(MathEscapeParser.PrimaryContext ctx) {
        if (ctx.INT() != null)
        {
            return new IntNode(Integer.parseInt(ctx.INT().getText()));
        } else if (ctx.ID() != null) {
            return new IdNode(ctx.ID().getText());
        } else   {
             return visit(ctx.expr());
        }

    }






}

