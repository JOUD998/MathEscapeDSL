package com.company.core.interpreter;

import com.company.core.model.*;
import com.company.generated_Grammars.MathEscapeBaseVisitor;
import com.company.generated_Grammars.MathEscapeParser;

public class MyMathVisitor extends MathEscapeBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitMathEscape(MathEscapeParser.MathEscapeContext ctx) {
//        System.out.println(ctx.toString());
        return visit(ctx.statement());
    }



    @Override
    public ASTNode visitStatement(MathEscapeParser.StatementContext ctx) {

        ASTNode left = new IdNode(ctx.ID().getText());
        ASTNode right = visit(ctx.expr());
        return new BinaryOpNode(left,'=',right);

    }

    @Override
    public ASTNode visitExpr(MathEscapeParser.ExprContext ctx) {

        if (ctx.expr() == null) {
            return visit(ctx.term());
        }

        ASTNode left = visit(ctx.expr());
        ASTNode right = visit(ctx.term());

        char op;
        if (ctx.PLUS() != null) {
            op = '+';
        } else if (ctx.MINUS() != null) {
            op = '-';
        } else {
            throw new RuntimeException("This operation is not allowed");
        }

        return new BinaryOpNode(left, op, right);
    }
    @Override
    public ASTNode visitTerm(MathEscapeParser.TermContext ctx) {

        if (ctx.term() == null) {
            return visit(ctx.factor());
        }

        ASTNode left = visit(ctx.term());
        ASTNode right = visit(ctx.factor());

        char op;
        if (ctx.MULTI() != null) {
            op = '*';
        } else if (ctx.DIV() != null) {
            op = '/';
        } else {
            throw new RuntimeException("This operation is not allowed");
        }

        return new BinaryOpNode(left, op, right);
    }

    @Override
    public ASTNode visitFactor(MathEscapeParser.FactorContext ctx) {
        ASTNode base = visit(ctx.postfix());

        // إذا فيه power: postfix ^ factor
        if (ctx.factor() != null) {
            ASTNode exponent = visit(ctx.factor());
            base = new PowerNode(base, exponent);
        }

        return base;
    }
    @Override
    public ASTNode visitPostfix(MathEscapeParser.PostfixContext ctx) {

        ASTNode base = visit(ctx.primary());


        if (ctx.FACT() != null) {
            base = new FactorialNode(base);
        }

        return base;



    }

    @Override
    public ASTNode visitPrimary(MathEscapeParser.PrimaryContext ctx) {
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