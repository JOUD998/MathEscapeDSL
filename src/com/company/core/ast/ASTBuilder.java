package com.company.core.ast;

import com.company.core.model.ASTNode;
import com.company.core.model.NumberLiteralNode;
import com.company.core.model.unit.BaseUnitNode;
import com.company.core.model.unit.UnitNode;
import com.company.grammar.MathDSLBaseVisitor;
import com.company.grammar.MathDSLParser;

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
        if (ctx.baseUnit().size() == 1){
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
        return super.visitExpr(ctx);
    }

    @Override
    public ASTNode visitTerm(MathDSLParser.TermContext ctx) {
        return super.visitTerm(ctx);
    }

    @Override
    public ASTNode visitFactor(MathDSLParser.FactorContext ctx) {
        return super.visitFactor(ctx);
    }

    @Override
    public ASTNode visitPostfix(MathDSLParser.PostfixContext ctx) {
        return super.visitPostfix(ctx);
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

        return super.visitPrimary(ctx);
    }
}
