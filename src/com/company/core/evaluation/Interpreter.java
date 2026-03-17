package com.company.core.evaluation;

import com.company.core.model.*;
import com.company.core.model.function.*;
import com.company.core.model.statment.StatementNode;
import com.company.core.model.unit.BaseUnitNode;
import com.company.core.model.unit.UnitNode;
import com.company.core.semantic.ASTVisitor;
import com.company.core.semantic.unit.Dimension;
import com.company.core.symbol_table.*;

public class Interpreter implements ASTVisitor<EvaluationResult> {

    private SymbolTable currentScope;

    public Interpreter(SymbolTable scope) {
        this.currentScope = scope;
    }

    @Override
    public EvaluationResult visitProgramNode(ProgramNode node) {
        EvaluationResult lastResult = null;
        for (ASTNode statement : node.statements) {
            lastResult = statement.accept(this);
        }
        return lastResult;
    }

    @Override
    public EvaluationResult visitStatementNode(StatementNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitExprStmtNode(ExprStmtNode node) {
        return node.expr.accept(this);
    }

    @Override
    public EvaluationResult visitVariableNode(VariableNode node) {
        EvaluationResult exprResult = node.expression.accept(this);

        Symbol symbol = currentScope.resolve(node.varId.name);
        Dimension finalDim = exprResult.dimension();

        if (symbol instanceof VariableSymbol vs) {
            if (!vs.getDimension().isNone()) {
                finalDim = vs.getDimension();
            }

            vs.setValue(exprResult.value());
        }

        return new EvaluationResult(exprResult.value(), finalDim);

    }

    @Override
    public EvaluationResult visitBinaryOpNode(BinaryOpNode node) {
        EvaluationResult left = node.left.accept(this);
        EvaluationResult right = node.right.accept(this);

        return switch (node.op) {
            case '+' -> new EvaluationResult(left.value() + right.value(), left.dimension());
            case '-' -> new EvaluationResult(left.value() - right.value(), left.dimension());
            case '*' -> new EvaluationResult(left.value() * right.value(), left.dimension().multiply(right.dimension()));
            case '/' -> new EvaluationResult(left.value() / right.value(), left.dimension().divide(right.dimension()));
            default -> throw new RuntimeException("Operator not implemented: " + node.op);
        };
    }

    @Override
    public EvaluationResult visitPowerNode(PowerNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitNumberLiteralNode(NumberLiteralNode node) {

        return new EvaluationResult(node.value, node.dimension);
    }

    @Override
    public EvaluationResult visitIntNode(IntNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitFactorialNode(FactorialNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitTermNode(TermNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitFunDeclNode(FunDeclNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitFunctionNode(FunctionNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitFuncCallNode(FuncCallNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitParamListNode(ParamListNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitParamNode(ParamNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitIdNode(IdNode node) {
        Symbol symbol = currentScope.resolve(node.name);

        if (symbol instanceof VariableSymbol vs) {
            return new EvaluationResult(vs.getValue(), vs.getDimension());
        }

        throw new RuntimeException("Error: Variable '" + node.name + "' is not defined.");
    }

    @Override
    public EvaluationResult visitUnitNode(UnitNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitBaseUnitNode(BaseUnitNode node) {
        return null;
    }

    @Override
    public EvaluationResult visitIfNode(IfNode node) {
        return null;
    }
}
