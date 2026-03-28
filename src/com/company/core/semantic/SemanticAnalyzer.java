package com.company.core.semantic;

import com.company.core.ast.*;
import com.company.core.ast.function.*;
import com.company.core.ast.statment.StatementNode;
import com.company.core.ast.unit.BaseUnitNode;
import com.company.core.ast.unit.UnitNode;
import com.company.core.unit.Dimension;
import com.company.core.unit.UnitRegistry;
import com.company.core.symbol_table.FunctionSymbol;
import com.company.core.symbol_table.Symbol;
import com.company.core.symbol_table.SymbolTable;
import com.company.core.symbol_table.VariableSymbol;

import java.util.List;

public class SemanticAnalyzer implements ASTVisitor<Void> {

    //my global symbol table, will be updated as we enter new scopes (like functions)
    public SymbolTable currentScope = new SymbolTable(null);

    //todo fun x(a) = a Error: Error: Variable 'a' is not defined.

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
            throw new RuntimeException("Semantic Error: function already declared: " + node.funcId.name);
        }
        Dimension declaredReturnDim = new Dimension(); // الافتراضي NONE
        if (node.returnType != null) {
            node.returnType.accept(this);
            declaredReturnDim = node.returnType.dimension;
        }

        FunctionSymbol funcSymbol = new FunctionSymbol(node.funcId.name, node.params, declaredReturnDim, node.body);
        previous.define(funcSymbol);

        try {
            SymbolTable functionScope = new SymbolTable(previous);
            funcSymbol.setScope(functionScope);
            currentScope = functionScope;

            if (node.params != null) {
                for (ParamNode param : node.params) {
                    param.accept(this);
                }
            }

            // 4. فحص جسم الدالة ومقارنة الوحدات
            if (node.body != null) {
                node.body.accept(this);
                Dimension bodyDim = node.body.dimension;



                if (!declaredReturnDim.isNone()) {
                    if (!bodyDim.equals(declaredReturnDim)) {
                        throw new RuntimeException("Return type mismatch: Expected " +
                                declaredReturnDim.toReadableString() + " but body is " + bodyDim.toReadableString());
                    }
                }
            }
        } catch (RuntimeException e) {
            previous.remove(node.funcId.name);
            throw e;
        } finally {
            currentScope = previous;
        }

        return null;
    }

    @Override
    public Void visitVariableNode(VariableNode node) {
        // 1. تحليل التعبير أولاً (عشان نعرف أبعاده إذا كانت 10m مثلاً)
        node.expression.accept(this);
        Dimension exprDim = node.expression.dimension;

        if (currentScope.existsInCurrentScope(node.varId.name)) {
            throw new RuntimeException("Semantic Error: variable already declared: " + node.varId.name);
        }

        Dimension finalDimension;

        if (node.unit != null) {
            node.unit.accept(this); // تحليل الوحدة الصريحة مثل :m
            Dimension explicitDim = node.unit.dimension;

            // حالة let x:s = 1m (خطأ تضارب)
            if (!exprDim.isNone() && !exprDim.equals(explicitDim)) {
                throw new RuntimeException("Semantic Error: Dimension mismatch! Expected " + explicitDim + " but got " + exprDim);
            }

            finalDimension = explicitDim; // نعتمد الوحدة الصريحة (حالة let x:m = 1)
        } else {
            // حالة let x = 1m (استنتاج النوع)
            finalDimension = exprDim;
        }

        // 4. تخزين الرمز بالبُعد الصحيح (مو NONE!)
        VariableSymbol symbol = new VariableSymbol(
                node.varId.name,
                finalDimension,
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
            node.dimension = new Dimension();
            throw new RuntimeException("Semantic Error: variable not declared: " + node.name);
        }

        VariableSymbol var = (VariableSymbol) symbol;
        node.dimension = var.getDimension();
        return null;
    }

    @Override
    public Void visitFuncCallNode(FuncCallNode node) {


        // 1️⃣ resolve symbol
        Symbol symbol = currentScope.resolve(node.funcId.name);

        // التأكد أن الاسم موجود
        if (symbol == null) {
            node.dimension = new Dimension();
            throw new RuntimeException("Semantic Error: function not declared: " + node.funcId.name);

        }

        // التأكد أنه Function
        if (!(symbol instanceof FunctionSymbol)) {
            node.dimension = new Dimension();
            throw new RuntimeException("Semantic Error: '" + node.funcId.name + "' is not a function");
        }

        FunctionSymbol funcSymbol = (FunctionSymbol) symbol;

        // 2️⃣ الحصول على parameters
        List<ParamNode> params = funcSymbol.getParameters();
        List<ASTNode> args = node.args;

        // 3️⃣ التحقق من عدد arguments
        if (params != null && args.size() != params.size()) {
            throw new RuntimeException("Semantic Error: wrong number of arguments in call to "
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


                if (!arg.dimension.equals(param.dimension)) {
                    throw new RuntimeException("Semantic Error: Argument " + (i + 1) + " in function '"
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
            if (!leftDim.equals(rightDim)) {
                throw new RuntimeException("Semantic Error: Cannot " + "(" + node.op + ")" + " " + leftDim + " and " + rightDim);
            }
            node.dimension = leftDim;
            node.toBaseFactor = node.left.toBaseFactor;
        } else if (node.op == '*') {
            node.dimension = leftDim.multiply(rightDim);
        } else if (node.op == '/') {
            node.dimension = leftDim.divide(rightDim);
        }
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
        node.base.accept(this);

        // 2. التحقق من أن الأس هو رقم مباشر (Literal) حصراً
        if (!(node.exponent instanceof NumberLiteralNode)) {
            throw new RuntimeException("Semantic Error: Exponent must be a literal integer (e.g., 2, 3). Expressions or variables are not allowed as exponents.");
        }

        NumberLiteralNode exp = (NumberLiteralNode) node.exponent;

        // 3. التأكد أنه عدد صحيح (Ganzzahlig)
        if (exp.value != Math.floor(exp.value)) {
            throw new RuntimeException("Semantic Error: Exponent must be an integer, not " + exp.value);
        }

        // 4. وسم العقدة بالأبعاد الجديدة
        // (استخدام ميثود scale التي تضرب أبعاد الأساس في قيمة الأس)
        node.dimension = node.base.dimension.scale(exp.value);

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
        }

        if (!node.isDivision()) {
            node.dimension = node.left.dimension;
            node.toBaseFactor = node.left.toBaseFactor;
        } else {
            node.dimension = node.left.dimension.divide(node.right.dimension);

            if (UnitRegistry.containsDimension(node.dimension)) {
                node.toBaseFactor = node.left.toBaseFactor / node.right.toBaseFactor;
            } else {
                node.toBaseFactor = 1.0;
                node.dimension = new Dimension();
                throw new RuntimeException("Unbekannte zusammengesetzte Einheit: " + node.dimension.toReadableString());
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
            throw new RuntimeException("Unbekannte Einheit: " + node.symbol);
        }

        return null;
    }

    @Override
    public Void visitIfNode(IfNode node) {
        node.condition.accept(this);
        node.thenBranch.accept(this);
        node.elseBranch.accept(this);

        if (!node.thenBranch.dimension.equals(node.elseBranch.dimension)) {
            node.dimension = new Dimension();
            throw new RuntimeException("Semantic Error: if branches must have same dimension");
        } else {
            node.dimension = node.thenBranch.dimension;
        }

        return null;
    }

}