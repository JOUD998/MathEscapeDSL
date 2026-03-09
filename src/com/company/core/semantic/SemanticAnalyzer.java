package com.company.core.semantic;
import com.company.core.model.*;
import com.company.core.model.function.*;
import com.company.core.model.statment.StatementNode;
import com.company.core.model.unit.BaseUnitNode;
import com.company.core.model.unit.UnitNode;
import com.company.core.semantic.unit.Dimension;
import com.company.core.semantic.unit.UnitInfo;
import com.company.core.semantic.unit.UnitRegistry;
import com.company.core.symbol_table.FunctionSymbol;
import com.company.core.symbol_table.SymbolTable;
import com.company.core.symbol_table.VariableSymbol;

public class SemanticAnalyzer implements ASTVisitor<Void> {

     public SymbolTable currentScope = new SymbolTable(null);


    @Override
    public Void visitProgramNode(ProgramNode node) {
        currentScope = new SymbolTable(null); // GLOBAL SCOPE

        for (ASTNode stmt : node.statements) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public Void visitExprStmtNode(ExprStmtNode node) {
        node.expr.accept(this);
        return null;
    }

    @Override
    public Void visitVariableNode(VariableNode node) {

        if (node.unit != null) {
            node.unit.accept(this);
        }

        // زيارة التعبير أولاً ليتم حساب dimension
        node.expression.accept(this);

        // الآن نعرف dimension
        node.dimension = node.expression.dimension;

        VariableSymbol symbol = new VariableSymbol(node.varId.name, node.dimension);

        if (currentScope.existsInCurrentScope(node.varId.name)) {
            System.out.println("Semantic Error: variable already declared: " + node.varId.name);
        } else {
            currentScope.define(symbol);
        }

        return null;

    }


    @Override
    public Void visitBinaryOpNode(BinaryOpNode node) {

        node.left.accept(this);
        node.right.accept(this);

        Dimension leftDim = node.left.dimension;
        Dimension rightDim = node.right.dimension;

        if (node.op == '+' || node.op == '-') {

            if (leftDim == rightDim) {

                node.dimension = leftDim;

                System.out.println(
                        "Dimension match: " + leftDim + " " + node.op + " " + rightDim
                );

            } else {

                node.dimension = Dimension.NONE;

                System.out.println(
                        "Semantic Error: Dimension mismatch: "
                                + leftDim + " " + node.op + " " + rightDim
                );
            }

        }
        else if (node.op == '*') {

            // حالياً لا نشتق dimension جديد
            node.dimension = Dimension.NONE;

        }
        else if (node.op == '/') {

            // مثال بسيط
            if (leftDim == Dimension.LENGTH && rightDim == Dimension.TIME) {

                node.dimension = Dimension.SPEED;

            } else {

                node.dimension = Dimension.NONE;

            }
        }

        System.out.println("BinaryOpNode Dimension: " + node.dimension);

        return null;
    }

    @Override
    public Void visitNumberLiteralNode(NumberLiteralNode node) {

        if (node.unitNode != null) {
            node.unitNode.accept(this);

            node.dimension = node.unitNode.dimension;
            node.toBaseFactor = node.unitNode.toBaseFactor;
        } else {
            node.dimension = Dimension.NONE;
            node.toBaseFactor = 1.0;
        }

        return null;
    }

    // باقي visit methods ممكن تتركها فارغة مؤقتًا
    @Override
    public Void visitPowerNode(PowerNode node) {
        return null;
    }

    @Override
    public Void visitIntNode(IntNode node) {
        return null;
    }

    @Override
    public Void visitFactorialNode(FactorialNode node) {
        return null;
    }

    @Override
    public Void visitTermNode(TermNode node) {
        return null;
    }

    @Override
    public Void visitFunDeclNode(FunDeclNode node) {


        // إنشاء scope جديد للدالة
        SymbolTable previous = currentScope;
        currentScope = new SymbolTable(previous);

        if (node.params != null) {
            for (ParamNode param : node.params) {
                param.accept(this);
            }        }

        node.body.accept(this);
        System.out.println("Finished visiting function: " + node.funcId.name);
        // العودة للـ scope السابق
        currentScope = previous;

        return null;    }

    @Override
    public Void visitFunctionNode(FunctionNode node) {
//        currentScope.define(new FunctionSymbol(node.getName().name, null, Dimension.NONE));
        return null;
    }

    @Override
    public Void visitFuncCallNode(FuncCallNode node) {
        return null;
    }

    @Override
    public Void visitParamListNode(ParamListNode node) {
        return null;
    }

    @Override
    public Void visitParamNode(ParamNode node) {

        node.unit.accept(this);

        VariableSymbol param = new VariableSymbol(node.name, node.dimension);

        currentScope.define(param);

        return null;
    }

    @Override
    public Void visitIdNode(IdNode node) {

        VariableSymbol symbol = (VariableSymbol) currentScope.resolve(node.name);

        if (symbol == null) {

            System.out.println("Semantic Error: variable not declared: " + node.name);

            node.dimension = Dimension.NONE;
            node.toBaseFactor = 1.0;

            return null;
        }

        // أخذ المعلومات من SymbolTable
        node.dimension = symbol.getDimension();

        return null;
    }

    @Override
    public Void visitStatementNode(StatementNode node) {
        return null;
    }


    @Override
    public Void visitBaseUnitNode(BaseUnitNode node) {

        if (UnitRegistry.UNIT_TABLE.containsKey(node.symbol)) {
            UnitInfo unitInfo = UnitRegistry.UNIT_TABLE.get(node.symbol);
            node.dimension = unitInfo.getUnitCategory();
            node.toBaseFactor = unitInfo.getToBaseFactor();

        } else {

            node.dimension = Dimension.NONE;
            node.toBaseFactor = 1.0;
            System.out.println("Unbekannte Einheit: " + node.symbol);
        }
        return null;
    }

    @Override
    public Void visitUnitNode(UnitNode node) {

        node.left.accept(this);
        if (node.isDivision()) {
            node.right.accept(this);
        }

        if (!node.isDivision()) {
            node.dimension = node.left.dimension;
            node.toBaseFactor = node.left.toBaseFactor;
        } else {
            // مثال: LENGTH / TIME → SPEED
            if (node.left.dimension == Dimension.LENGTH && node.right.dimension == Dimension.TIME) {
                node.dimension = Dimension.SPEED;
                node.toBaseFactor = node.left.toBaseFactor / node.right.toBaseFactor;
            } else {
                node.dimension = Dimension.NONE;
                node.toBaseFactor = 1.0;
                System.out.println("Invalid unit division: " + node.left.symbol + " / " + node.right.symbol);
            }
        }

        System.out.println("Visiting UnitNode: " + node.left.symbol +
                (node.isDivision() ? " / " + node.right.symbol : "") +
                " -> Dimension: " + node.dimension + ", Factor: " + node.toBaseFactor);

        return null;
    }


}