package com.company.core.semantic;

import com.company.core.model.*;
import com.company.core.model.function.*;
import com.company.core.model.statment.StatementNode;
import com.company.core.model.unit.BaseUnitNode;
import com.company.core.model.unit.UnitNode;
import com.company.core.semantic.unit.Dimension;
import com.company.core.semantic.unit.UnitRegistry;
import com.company.core.symbol_table.FunctionSymbol;
import com.company.core.symbol_table.Symbol;
import com.company.core.symbol_table.SymbolTable;
import com.company.core.symbol_table.VariableSymbol;

import java.util.List;

public class SemanticAnalyzer implements ASTVisitor<Void> {

    //my global symbol table, will be updated as we enter new scopes (like functions)
    public SymbolTable currentScope = new SymbolTable(null);

    // -------------------------
    // prog → statement* EOF
    // -------------------------
    @Override
    public Void visitProgramNode(ProgramNode node) {
//        currentScope = new SymbolTable(null); // GLOBAL SCOPE
        for (ASTNode stmt : node.statements) {
            stmt.accept(this);
        }
        return null;
    }

    // -------------------------
    // Statements
    // -------------------------

    @Override
    public Void visitFunDeclNode(FunDeclNode node) {
        SymbolTable previous = currentScope;

        if (previous.existsInCurrentScope(node.funcId.name)) {
            System.out.println("Semantic Error: function already declared: " + node.funcId.name);
            return null;
        }

        // تعريف الدالة في الـ global scope
        FunctionSymbol funcSymbol = new FunctionSymbol(node.funcId.name, node.params, node.dimension);
        previous.define(funcSymbol);

        // إنشاء scope خاص بالدالة
        SymbolTable functionScope = new SymbolTable(previous);
        funcSymbol.setScope(functionScope);
        currentScope = functionScope;

        // إضافة parameters إلى الـ scope الجديد
        if (node.params != null) {
            for (ParamNode param : node.params) {
                System.out.println("Parameter: " + param.name);
                param.accept(this); // visitParamNode
            }
        }

        // زيارة جسم الدالة
        if (node.body != null) {
            node.body.accept(this);
        }

        // العودة للـ scope السابق
        currentScope = previous;

        return null;
    }

    @Override
    public Void visitVariableNode(VariableNode node) {

        if (node.unit != null) {
            node.unit.accept(this);
        }

        node.expression.accept(this);

        if (currentScope.existsInCurrentScope(node.varId.name)) {
            System.out.println("Semantic Error: variable already declared: " + node.varId.name);
            return null;
        }

        Dimension dimensionEnum = (node.unit != null) ? node.unit.dimension : new Dimension();

        VariableSymbol symbol = new VariableSymbol(
                node.varId.name,
                dimensionEnum,
                node.expression
        );

        currentScope.define(symbol);

        return null;
    }

    @Override
    public Void visitExprStmtNode(ExprStmtNode node) {
        node.expr.accept(this);
        return null;
    }

    @Override
    public Void visitStatementNode(StatementNode node) {
        return null;
    }

    // -------------------------
    // Parameters & Arguments
    // -------------------------
    @Override
    public Void visitParamListNode(ParamListNode node) {
        if (node.paramNodeList != null) {
            for (ParamNode param : node.paramNodeList) {
                param.accept(this); // كل ParamNode يضيف نفسه للـ currentScope
            }
        }
        return null;
    }

    @Override
    public Void visitParamNode(ParamNode node) {
        if (node.unit != null) {
            node.unit.accept(this);
            node.dimension = node.unit.dimension;
        }
        VariableSymbol param = new VariableSymbol(node.name, node.dimension, null);
        currentScope.define(param);
        return null;
    }

    @Override
    public Void visitIdNode(IdNode node) {

        Symbol symbol = currentScope.resolve(node.name);

        if (!(symbol instanceof VariableSymbol)) {
            System.out.println("Semantic Error: variable not declared: " + node.name);
            node.dimension = new Dimension();
            return null;
        }

        VariableSymbol var = (VariableSymbol) symbol;
        node.dimension = var.getDimension();
        return null;
    }

    @Override
    public Void visitFuncCallNode(FuncCallNode node) {

        System.out.println("Function Calling: " + node.funcId.name);

        // 1️⃣ resolve symbol
        Symbol symbol = currentScope.resolve(node.funcId.name);

        // التأكد أن الاسم موجود
        if (symbol == null) {
            System.out.println("Semantic Error: function not declared: " + node.funcId.name);
            node.dimension = new Dimension();
            return null;
        }

        // التأكد أنه Function
        if (!(symbol instanceof FunctionSymbol)) {
            System.out.println("Semantic Error: '" + node.funcId.name + "' is not a function");
            node.dimension = new Dimension();
            return null;
        }

        FunctionSymbol funcSymbol = (FunctionSymbol) symbol;

        // 2️⃣ الحصول على parameters
        List<ParamNode> params = funcSymbol.getParameters();
        List<ASTNode> args = node.args;

        // 3️⃣ التحقق من عدد arguments
        if (params != null && args.size() != params.size()) {
            System.out.println("Semantic Error: wrong number of arguments in call to "
                    + node.funcId.name +
                    ". Expected " + params.size() +
                    " but got " + args.size());
        }

        // 4️⃣ زيارة arguments ومقارنتها مع parameters
        for (int i = 0; i < args.size(); i++) {

            ASTNode arg = args.get(i);
            arg.accept(this);

            if (params != null && i < params.size()) {

                ParamNode param = params.get(i);

                System.out.println("Arg " + (i + 1) + " -> " + arg.dimension +
                        " | Param -> " + param.dimension);

                if (!arg.dimension.equals(param.dimension)) {
                    System.out.println("Semantic Error: Argument " + (i + 1) + " in function '"
                            + node.funcId.name +
                            "' has dimensionEnum mismatch. Expected: "
                            + param.dimension +
                            ", but got: " + arg.dimension + ".");
                }
            }
        }

        // 5️⃣ تحديد dimensionEnum الناتجة من الدالة
        node.dimension = funcSymbol.getDimension();

        return null;
    }

    //Das ist interface
    @Override
    public Void visitFunctionNode(FunctionNode node) {
        return null;
    }

    // -------------------------
    // Expressions
    // -------------------------
    @Override
    public Void visitBinaryOpNode(BinaryOpNode node) {
        node.left.accept(this);
        node.right.accept(this);

        Dimension leftDim = node.left.dimension;
        Dimension rightDim = node.right.dimension;

        if (node.op == '+' || node.op == '-') {
            if (leftDim.equals(rightDim)) {
                node.dimension = leftDim;
                System.out.println("Dimension match: " + leftDim + " " + node.op + " " + rightDim);
            } else {
                node.dimension = new Dimension();
                System.out.println("Semantic Error: DimensionEnum mismatch: " + leftDim + " " + node.op + " " + rightDim);
            }

            //todo: handle unit conversion
        } else if (node.op == '*') {
        } else if (node.op == '/') {
        }

        System.out.println("BinaryOpNode DimensionEnum: " + node.dimension);
        return null;
    }

    @Override
    public Void visitTermNode(TermNode node) {
        return null;
    }

    @Override
    public Void visitFactorialNode(FactorialNode node) {
        return null;
    }

    @Override
    public Void visitPowerNode(PowerNode node) {
        return null;
    }

    @Override
    public Void visitNumberLiteralNode(NumberLiteralNode node) {
        if (node.unitNode != null) {
            node.unitNode.accept(this);
            node.dimension = node.unitNode.dimension;
            node.toBaseFactor = node.unitNode.toBaseFactor;
        } else {
            node.dimension = new Dimension();
            node.toBaseFactor = 1.0;
        }
        return null;
    }

    @Override
    public Void visitIntNode(IntNode node) {
        return null;
    }

    // -------------------------
    // Units
    // -------------------------
    @Override
    public Void visitUnitNode(UnitNode node) {

        node.left.accept(this);

        if (node.isDivision()) {
            node.right.accept(this);
            String s = node.left.dimension.toString() + " / " + node.right.dimension.toString();
            System.out.println("UnitNode: " + s);
        }

        if (!node.isDivision()) {
            node.dimension = node.left.dimension;
            node.toBaseFactor = node.left.toBaseFactor;
        } else {
            node.dimension = node.left.dimension.divide(node.right.dimension);

            System.out.println("UnitNode DimensionEnum: " + node.dimension.toString());
            System.out.println(node.dimension.length + " " + node.dimension.time + " " + node.dimension.mass);
            if (UnitRegistry.containsDimension(node.dimension)) {
                node.toBaseFactor = node.left.toBaseFactor / node.right.toBaseFactor;
            } else {
                node.toBaseFactor = 1.0;
                node.dimension = new Dimension();
                System.out.println("Unbekannte zusammengesetzte Einheit: " + node.dimension.toReadableString());
            }

        }


        return null;
    }

    @Override
    public Void visitBaseUnitNode(BaseUnitNode node) {
        if (UnitRegistry.UNIT_TABLE.containsKey(node.symbol)) {

            node.dimension = UnitRegistry.UNIT_TABLE.get(node.symbol).getDimension();
            node.toBaseFactor = UnitRegistry.UNIT_TABLE.get(node.symbol).getToBaseFactor();
        } else {
            node.dimension = new Dimension();
            node.toBaseFactor = 1.0;
            System.out.println("Unbekannte Einheit: " + node.symbol);
        }

        return null;
    }

    @Override
    public Void visitIfNode(IfNode node) {
        node.condition.accept(this);
        node.thenBranch.accept(this);
        node.elseBranch.accept(this);

        if (!node.thenBranch.dimension.equals(node.elseBranch.dimension)) {
            System.out.println("Semantic Error: if branches must have same dimension");
            node.dimension = new Dimension();
        } else {
            node.dimension = node.thenBranch.dimension;
        }

        return null;
    }

}