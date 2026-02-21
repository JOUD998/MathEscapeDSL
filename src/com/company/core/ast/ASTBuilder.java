package com.company.core.ast;

import com.company.core.model.*;
import com.company.core.model.unit.BaseUnitNode;
import com.company.core.model.unit.UnitNode;
import com.company.grammar.MathDSLBaseVisitor;
import com.company.grammar.MathDSLParser;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends MathDSLBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProg(MathDSLParser.ProgContext ctx) {
        return super.visitProg(ctx);
    }

    @Override
    public ASTNode visitStatement(MathDSLParser.StatementContext ctx) {
        return super.visitStatement(ctx);
    }

    @Override
    public ASTNode visitFunDecl(MathDSLParser.FunDeclContext ctx) {
        return super.visitFunDecl(ctx);
    }

    @Override
    public ASTNode visitLetDecl(MathDSLParser.LetDeclContext ctx) {
        return super.visitLetDecl(ctx);
    }

    @Override
    public ASTNode visitExprStmt(MathDSLParser.ExprStmtContext ctx) {
        return super.visitExprStmt(ctx);
    }

    @Override
    public ASTNode visitParamList(MathDSLParser.ParamListContext ctx) {
        return super.visitParamList(ctx);
    }

    @Override
    public ASTNode visitParam(MathDSLParser.ParamContext ctx) {
        return super.visitParam(ctx);
    }

    @Override
    public ASTNode visitArgList(MathDSLParser.ArgListContext ctx) {
        return super.visitArgList(ctx);
    }

    @Override
    public ASTNode visitUnit(MathDSLParser.UnitContext ctx) {
        if (ctx.baseUnit().size() == 1) {
            BaseUnitNode left = (BaseUnitNode) visit(ctx.baseUnit(0));
            return new UnitNode(left);
        }
        BaseUnitNode left = (BaseUnitNode) visit(ctx.baseUnit(0));
        BaseUnitNode right = (BaseUnitNode) visit(ctx.baseUnit(1));
        return new UnitNode(left, right);
    }

    @Override
    public ASTNode visitBaseUnit(MathDSLParser.BaseUnitContext ctx) {
        return new BaseUnitNode(ctx.getText());
    }

    @Override
    public ASTNode visitExpr(MathDSLParser.ExprContext ctx) {
        if (ctx.PLUS() != null || ctx.MINUS() != null) {
            ASTNode leftSide = visit(ctx.expr());
            ASTNode rightSide = visit(ctx.term());
            char op = ctx.PLUS() != null ? '+' : '-';
            return new BinaryOpNode(leftSide, op, rightSide);
        } else {
            return visit(ctx.term());
        }
    }

    @Override
    public ASTNode visitTerm(MathDSLParser.TermContext ctx) {
        if (ctx.MULTI() != null || ctx.DIV() != null) {
            ASTNode leftSide = visit(ctx.term());
            ASTNode rightSide = visit(ctx.factor());
            char op = ctx.MULTI() != null ? '*' : '/';
            return new BinaryOpNode(leftSide, op, rightSide);
        } else {
            return visit(ctx.factor());
        }

    }

    @Override
    public ASTNode visitFactor(MathDSLParser.FactorContext ctx) {
        if (ctx.POWER() != null) {
            ASTNode base = visit(ctx.primary());
            ASTNode exponent = visit(ctx.factor());
            return new PowerNode(base, exponent);
        } else {
            return visit(ctx.primary());
        }
    }


    @Override
    public ASTNode visitPrimary(MathDSLParser.PrimaryContext ctx) {

        if (ctx.NUMBER() != null) {
            double value = Double.parseDouble(ctx.NUMBER().getText());
            UnitNode unit = null;
            if (ctx.unit() != null) {
                unit = (UnitNode) visit(ctx.unit());
            }
            return new NumberLiteralNode(value, unit);
        }
        // متغير أو استدعاء تابع
        else if (ctx.ID() != null) {
            // استدعاء تابع
            if (ctx.LPAREN() != null && ctx.RPAREN() != null) {
                List<ParamNode> params = new ArrayList<>();
                if (ctx.argList() != null) {
                    for (MathDSLParser.ExprContext exprCtx : ctx.argList().expr()) {
                        // كل expr يتحول لنوع ASTNode أو NumberLiteralNode حسب ما تعلمنا
                        ASTNode argNode = visit(exprCtx);
                        if (argNode instanceof NumberLiteralNode) {
                            NumberLiteralNode numNode = (NumberLiteralNode) argNode;
                            params.add(new ParamNode("arg", numNode.unitNode)); // اسم افتراضي "arg"، ممكن تطوره لاحقاً
                        } else if (argNode instanceof IdNode) {
                            params.add(new ParamNode(((IdNode) argNode).name, null));
                        } else {
                            params.add(new ParamNode("expr", null));
                        }
                    }
                }
                return new FunDeclNode(new IdNode(ctx.ID().getText()), params);
            }
            // متغير عادي
            else {
                return new IdNode(ctx.ID().getText());
            }
        }

        return super.visitPrimary(ctx);
    }
}

