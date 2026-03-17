package com.company.core.semantic;

import com.company.core.model.*;
import com.company.core.model.function.*;
import com.company.core.model.statment.StatementNode;
import com.company.core.model.unit.BaseUnitNode;
import com.company.core.model.unit.UnitNode;
import com.company.core.semantic.unit.Dimension;
import com.company.core.semantic.unit.UnitRegistry;
import com.company.core.symbol_table.Symbol;
import com.company.core.symbol_table.SymbolTable;
import com.company.core.symbol_table.VariableSymbol;

public class TermRewriter implements ASTVisitor<ASTNode> {

    private SymbolTable currentScope;

    public TermRewriter(SymbolTable scope) {
        this.currentScope = scope;
    }





    //TODO node.accept(this) statements delete some and add some to rewrite the term

    @Override
    public ASTNode visitProgramNode(ProgramNode node) {
        for (int i = 0; i < node.statements.size(); i++) {
            ASTNode newStmt = node.statements.get(i).accept(this);
            if (newStmt != null) {
                node.statements.set(i, newStmt);
            }
        }
        return node;
    }

    //abstract class
    @Override
    public ASTNode visitStatementNode(StatementNode node) {
        return node;
    }

    @Override
    public ASTNode visitExprStmtNode(ExprStmtNode node) {
        ASTNode newExpr = node.expr.accept(this);
        if (newExpr != null) node.expr = newExpr;
        return node;
    }

    @Override
    public ASTNode visitVariableNode(VariableNode node) {
        if (node.unit != null) {
            ASTNode newUnit = node.unit.accept(this);
            if (newUnit != null) node.unit = (UnitNode) newUnit;
        }
        if (node.expression != null) {
            ASTNode newExpr = node.expression.accept(this);
            if (newExpr != null) node.expression = newExpr;
        }
        if (node.varId != null) {
            node.varId.accept(this);
        }
        return node;
    }

    @Override
    public ASTNode visitBinaryOpNode(BinaryOpNode node) {
        ASTNode newLeft = node.left.accept(this);
        if (newLeft != null) node.left = newLeft;
        ASTNode newRight = node.right.accept(this);
        if (newRight != null) node.right = newRight;
        //I have always here numbers with simplified units (like 5m, no 5 km)
        // 0,1 Simplification
        // Constance folding

        if (node.op == '*') {

            // (Algebraic Rules)
            if (node.left instanceof NumberLiteralNode l) {
                if (l.value == 1) return node.right;
                if (l.value == 0) return new NumberLiteralNode(0, null);
            }
            if (node.right instanceof NumberLiteralNode r) {
                if (r.value == 1) return node.left;
                if (r.value == 0) return new NumberLiteralNode(0, null);
            }

            //  (Constant Folding)
            if (node.left instanceof NumberLiteralNode l && node.right instanceof NumberLiteralNode r) {
                Dimension newDim = l.dimension.multiply(r.dimension);
                NumberLiteralNode result = new NumberLiteralNode((l.value * r.value), new UnitNode(new BaseUnitNode("Correct Unit in Dimension: " + newDim.toReadableString())));
                result.dimension = newDim;
                return result;
            }


        }

        else if (node.op == '/') {
            // (Algebraic Rules)
            if (node.right instanceof NumberLiteralNode r) {
                if (r.value == 1) return node.left; // x / 1 = x
                if (r.value == 0) throw new ArithmeticException("Division by zero"); // x / 0 = error
            }
            if (node.left instanceof NumberLiteralNode l) {
                if (l.value == 0) return new NumberLiteralNode(0, null); // 0 / x = 0 (for x != 0)
            }
            //  (Constant Folding)
            if (node.left instanceof NumberLiteralNode l && node.right instanceof NumberLiteralNode r) {
                Dimension newDim = l.dimension.divide(r.dimension);
                NumberLiteralNode result = new NumberLiteralNode((l.value / r.value), new UnitNode(new BaseUnitNode("Correct Unit in Dimension: " + newDim.toReadableString())));
                result.dimension = newDim;
                return result;
            }

        }

        else if (node.op == '+') {
            // (Algebraic Rules)
            if (node.right instanceof NumberLiteralNode r) {
                if (r.value == 0) return node.left; // x + 0 = x
            }
            if (node.left instanceof NumberLiteralNode l) {
                if (l.value == 0) return node.right; // 0 + x = x
            }
            //  (Constant Folding)
            if (node.left instanceof NumberLiteralNode l && node.right instanceof NumberLiteralNode r) {
                NumberLiteralNode result = new NumberLiteralNode((l.value + r.value), l.unitNode);
                result.dimension = l.dimension.copy();
                return result;            }

        }

        else if (node.op == '-') {
            // (Algebraic Rules)
            if (node.right instanceof NumberLiteralNode r) {
                if (r.value == 0) return node.left; // x - 0 = x
            }
            if (node.left instanceof NumberLiteralNode l) {
                if (l.value == 0){
                    if (node.right instanceof NumberLiteralNode r){
                        NumberLiteralNode result = new NumberLiteralNode((l.value - r.value), l.unitNode);
                        result.dimension = r.dimension.copy();
                        return result;
                    }
                }


            }
            //  (Constant Folding)
            if (node.left instanceof NumberLiteralNode l && node.right instanceof NumberLiteralNode r) {
                NumberLiteralNode result = new NumberLiteralNode((l.value - r.value), new UnitNode(new BaseUnitNode("Correct Unit in Dimension: " + l.dimension.toReadableString())));
                result.dimension = l.dimension.copy();
                result.unitNode = l.unitNode;
                return result;
            }

        }

        return node;
    }

    @Override
    public ASTNode visitPowerNode(PowerNode node) {
        ASTNode base = node.base.accept(this);
        ASTNode exponent = node.exponent.accept(this);

        // تحديث العقدة الأصلية
        node.base = base;
        node.exponent = exponent;

        // 2. تبسيط الأسس (Algebraic Rules)
        if (exponent instanceof NumberLiteralNode exp) {
            // أي شيء أس 0 هو 1 (وبدون وحدة Dimensionless)
            if (exp.value == 0) {
                NumberLiteralNode result = new NumberLiteralNode(1, null);
                result.dimension = new Dimension(0, 0, 0); // هامة جداً!
                return result;
            }
            // أي شيء أس 1 يبقى كما هو
            if (exp.value == 1) {
                return base;
            }
        }

        // 3. طي الثوابت (Constant Folding & Dimension Scaling)
        if (base instanceof NumberLiteralNode b && exponent instanceof NumberLiteralNode e) {
            // حساب القيمة الحسابية
            double newValue = Math.pow(b.value, e.value);

            // حساب الأبعاد الجديدة: ضرب أبعاد الأساس في قيمة الأس
            Dimension newDim = b.dimension.scale(e.value);

            NumberLiteralNode result = new NumberLiteralNode(newValue, null);
            result.dimension = newDim;

            // تسمية الوحدة للـ REPL (مؤقتاً)
            result.unitNode = new UnitNode(new BaseUnitNode("Unit: " + newDim.toReadableString()));

            return result;
        }

        return node;
    }

    @Override
    public ASTNode visitNumberLiteralNode(NumberLiteralNode node) {

        if (node.unitNode != null) {
            node.unitNode = (UnitNode) node.unitNode.accept(this);

            double factor = node.unitNode.toBaseFactor;
            if (factor != 1.0) {
                node.value *= factor;
                node.unitNode.toBaseFactor = 1.0;
                normalizeToStandardUnit(node);
            }

        }

        return node;
    }

    @Override
    public ASTNode visitIntNode(IntNode node) {
        return node;
    }

    @Override
    public ASTNode visitFactorialNode(FactorialNode node) {
        ASTNode newTerm = node.child.accept(this);
        if (newTerm != null) node.child = newTerm;
        return node;
    }

    @Override
    public ASTNode visitTermNode(TermNode node) {
        return node;
    }

    @Override
    public ASTNode visitFunDeclNode(FunDeclNode node) {
        return node;
    }

    @Override
    public ASTNode visitFunctionNode(FunctionNode node) {
        return null;
    }

    @Override
    public ASTNode visitFuncCallNode(FuncCallNode node) {
        return node;
    }

    @Override
    public ASTNode visitParamListNode(ParamListNode node) {
        return node;
    }

    @Override
    public ASTNode visitParamNode(ParamNode node) {
        return node;
    }

    @Override
    public ASTNode visitIdNode(IdNode node) {
        Symbol symbol = currentScope.resolve(node.name);

        if (symbol instanceof VariableSymbol vs) {
            if (vs.getExpression() instanceof NumberLiteralNode num) {
                NumberLiteralNode copy = new NumberLiteralNode(num.value, num.unitNode);
                copy.dimension = vs.getDimension(); // تأكد إن الـ dimension هون مو null أو NONE
                return copy;
            }
        }
        return node;
    }

    @Override
    public ASTNode visitUnitNode(UnitNode node) {
        ASTNode newLeft = node.left.accept(this);
        if (newLeft != null) node.left = (BaseUnitNode) newLeft;

        if (node.right != null) {
            ASTNode newRight = node.right.accept(this);
            if (newRight != null) node.right = (BaseUnitNode) newRight;
        }

        return node;
    }


    @Override
    public ASTNode visitBaseUnitNode(BaseUnitNode node) {

        return node;
    }

    @Override
    public ASTNode visitIfNode(IfNode node) {
        ASTNode newCond = node.condition.accept(this);
        if (newCond != null) node.condition = newCond;
        ASTNode newThen = node.thenBranch.accept(this);
        if (newThen != null) node.thenBranch = newThen;
        if (node.elseBranch != null) {
            ASTNode newElse = node.elseBranch.accept(this);
            if (newElse != null) node.elseBranch = newElse;
        }
        return node;
    }


    private void normalizeToStandardUnit(NumberLiteralNode node) {
        String symbol = UnitRegistry.getBaseUnitSymbol(node.dimension);

        if (symbol.equals("NONE")) return;

        if (symbol.contains("/")) {
            String[] parts = symbol.split("/");
            node.unitNode = new UnitNode(new BaseUnitNode(parts[0]), new BaseUnitNode(parts[1]));
        } else {
            node.unitNode = new UnitNode(new BaseUnitNode(symbol));
        }
    }
//    private boolean areUnitsCompatible(NumberLiteralNode l, NumberLiteralNode r) {
//        // إذا كان الطرفان بلا وحدات، فهما متوافقان (حساب أرقام عادي)
//        if (l.unitNode == null && r.unitNode == null) return true;
//
//        // إذا كان أحدهما بوحدة والآخر لا، فهذا خطأ في العمليات مثل (5m + 2)
//        if (l.unitNode == null || r.unitNode == null) return false;
//
//        // إذا كلاهما بوحدات، نستخدم الـ equals التي كتبناها سابقاً (أو نقارن الأبعاد Dimension)
//        return l.unitNode.equals(r.unitNode);
//    }
}

