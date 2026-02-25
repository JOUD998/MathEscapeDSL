package com.company.core.ast;
import com.company.core.model.*;
import com.company.core.model.function.FunDeclNode;
import com.company.core.model.function.FuncCallNode;
import com.company.core.model.function.ParamListNode;
import com.company.core.model.function.ParamNode;
import com.company.core.model.unit.BaseUnitNode;
import com.company.core.model.unit.UnitNode;
import com.company.grammar.MathDSLBaseVisitor;
import com.company.grammar.MathDSLParser;
import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends MathDSLBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProg(MathDSLParser.ProgContext ctx) {
        List<ASTNode> statements = new ArrayList<>();
        for (MathDSLParser.StatementContext statement: ctx.statement()){
            statements.add(visit(statement));
        }
        return new ProgramNode(statements);
    }

    @Override
    public ASTNode visitStatement(MathDSLParser.StatementContext ctx) {
        if (ctx.funDecl() != null) {
            return visit(ctx.funDecl());
        } else if (ctx.letDecl() != null) {
            return visit(ctx.letDecl());
        } else {
            return visit(ctx.exprStmt());
        }
    }


    @Override
    public ASTNode visitFunDecl(MathDSLParser.FunDeclContext ctx) {

        IdNode id = new IdNode(ctx.fname.getText());

        List<ParamNode> params;
        if (ctx.paramList() != null) {
            ParamListNode listNode = (ParamListNode) visit(ctx.paramList());
            params = listNode.paramNodeList;

        } else {
            params = new ArrayList<>();
        }

        UnitNode returnType = null;
        if (ctx.unit() != null) {
            returnType = (UnitNode) visit(ctx.unit());
        }

        ASTNode body = visit(ctx.expr());

        return new FunDeclNode(id, params, returnType, body);
    }

    @Override
    public ASTNode visitLetDecl(MathDSLParser.LetDeclContext ctx) {
//            : LET vname=ID (COLON unit)? EQUAL expr
        UnitNode unit = null;
        if (ctx.unit() != null){
            unit = (UnitNode) visit(ctx.unit());
        }

        return new VariableNode(new IdNode(ctx.ID().getText()),unit,visit(ctx.expr()));
    }

    @Override
    public ASTNode visitExprStmt(MathDSLParser.ExprStmtContext ctx) {
        ASTNode exprNode = visit(ctx.expr());
        return new ExprStmtNode(exprNode);
    }

    @Override
    public ASTNode visitParamList(MathDSLParser.ParamListContext ctx) {
        List<ParamNode> paramNodeList = new ArrayList<>();
        for (MathDSLParser.ParamContext pCtx : ctx.param()) {
            paramNodeList.add((ParamNode) visit(pCtx));
        }
        return new ParamListNode(paramNodeList);
    }

    @Override
    public ASTNode visitParam(MathDSLParser.ParamContext ctx) {
        UnitNode unit = null;
        if (ctx.unit() != null) {
            unit = (UnitNode) visit(ctx.unit());
        }
        return new ParamNode(ctx.pname.getText(), unit);

    }

    //to delete
    @Override
    public ASTNode visitArgList(MathDSLParser.ArgListContext ctx) {
//        List<ASTNode> expressions = new ArrayList<>();
//        for (MathDSLParser.ExprContext exps: ctx.expr()){
//            expressions.add(visit(exps));
//        }
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
        } else if (ctx.ID() != null) {
            if (ctx.LPAREN() != null && ctx.RPAREN() != null) {
                List<ASTNode> args = new ArrayList<>();
                if (ctx.argList() != null) {
                    for (MathDSLParser.ExprContext exprCtx : ctx.argList().expr()) {
                        args.add(visit(exprCtx));
                    }
                }
                return new FuncCallNode(
                        new IdNode(ctx.ID().getText()),
                        args
                );
            } else {
                return new IdNode(ctx.ID().getText());
            }
        }

        return super.visitPrimary(ctx);
    }


}

