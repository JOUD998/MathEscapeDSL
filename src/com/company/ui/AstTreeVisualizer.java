package com.company.ui;

import com.company.core.ast.*;
import com.company.core.ast.function.*;
import com.company.core.ast.statment.StatementNode;
import com.company.core.ast.unit.BaseUnitNode;
import com.company.core.ast.unit.UnitNode;
import com.company.core.semantic.ASTVisitor;
import javafx.scene.control.TreeItem;

public class AstTreeVisualizer implements ASTVisitor<TreeItem<String>> {

    @Override
    public TreeItem<String> visitProgramNode(ProgramNode node) {
        TreeItem<String> root = new TreeItem<>("📜 Program");
        if (node.statements != null) {
            for (ASTNode stmt : node.statements) {
                root.getChildren().add(stmt.accept(this));
            }
        }
        return root;
    }

    @Override
    public TreeItem<String> visitStatementNode(StatementNode node) {
        return new TreeItem<>("Statement");
    }

    @Override
    public TreeItem<String> visitExprStmtNode(ExprStmtNode node) {
        TreeItem<String> item = new TreeItem<>("Expression Stmt");
        if (node.expr != null) {
            item.getChildren().add(node.expr.accept(this));
        }
        return item;
    }

    @Override
    public TreeItem<String> visitVariableNode(VariableNode node) {
        String name = (node.varId != null) ? node.varId.name : "unknown";
        TreeItem<String> item = new TreeItem<>("Variable: " + name);
        if (node.expression != null) {
            item.getChildren().add(node.expression.accept(this));
        }
        if (node.unit != null) {
            item.getChildren().add(node.unit.accept(this));
        }
        return item;
    }

    @Override
    public TreeItem<String> visitBinaryOpNode(BinaryOpNode node) {
        TreeItem<String> item = new TreeItem<>("Binary Op [" + node.op + "]");
        if (node.left != null) item.getChildren().add(node.left.accept(this));
        if (node.right != null) item.getChildren().add(node.right.accept(this));
        return item;
    }

    @Override
    public TreeItem<String> visitPowerNode(PowerNode node) {
        TreeItem<String> item = new TreeItem<>("Power (^)");
        if (node.base != null) item.getChildren().add(node.base.accept(this));
        if (node.exponent != null) item.getChildren().add(node.exponent.accept(this));
        return item;
    }

    @Override
    public TreeItem<String> visitNumberLiteralNode(NumberLiteralNode node) {
        String label = "Number: " + node.value;
        TreeItem<String> item = new TreeItem<>(label);
        if (node.unitNode != null) {
            item.getChildren().add(node.unitNode.accept(this));
        }
        return item;
    }

    @Override
    public TreeItem<String> visitIntNode(IntNode node) {
        return new TreeItem<>("Integer: " + node.value);
    }

    @Override
    public TreeItem<String> visitFactorialNode(FactorialNode node) {
        TreeItem<String> item = new TreeItem<>("Factorial (!)");
        if (node.child != null) {
            item.getChildren().add(node.child.accept(this));
        }
        return item;
    }

    @Override
    public TreeItem<String> visitTermNode(TermNode node) {
        return new TreeItem<>("Term");
    }

    @Override
    public TreeItem<String> visitFunDeclNode(FunDeclNode node) {
        String funcName = (node.funcId != null) ? node.funcId.name : "anonymous";
        TreeItem<String> item = new TreeItem<>("Function Decl: " + funcName);

        if (node.params != null) {
            TreeItem<String> params = new TreeItem<>("Params");
            for (ParamNode p : node.params) {
                params.getChildren().add(p.accept(this));
            }
            item.getChildren().add(params);
        }

        if (node.body != null) {
            TreeItem<String> body = new TreeItem<>("Body");
            body.getChildren().add(node.body.accept(this));
            item.getChildren().add(body);
        }

        return item;
    }

    @Override
    public TreeItem<String> visitFunctionNode(FunctionNode node) {
        return new TreeItem<>("Function: " + node.getName());
    }

    @Override
    public TreeItem<String> visitFuncCallNode(FuncCallNode node) {
        String funcName = (node.funcId != null) ? node.funcId.name : "unknown";
        TreeItem<String> item = new TreeItem<>("Call: " + funcName);
        if (node.args != null) {
            for (ASTNode arg : node.args) {
                item.getChildren().add(arg.accept(this));
            }
        }
        return item;
    }

    @Override
    public TreeItem<String> visitParamListNode(ParamListNode node) {
        TreeItem<String> item = new TreeItem<>("Parameter List");
        if (node.paramNodeList != null) {
            for (ParamNode p : node.paramNodeList) {
                item.getChildren().add(p.accept(this));
            }
        }
        return item;
    }

    @Override
    public TreeItem<String> visitParamNode(ParamNode node) {
        String label = "Param: " + node.name;
        TreeItem<String> item = new TreeItem<>(label);
        if (node.unit != null) {
            item.getChildren().add(node.unit.accept(this));
        }
        return item;
    }

    @Override
    public TreeItem<String> visitIdNode(IdNode node) {
        return new TreeItem<>("ID: " + node.name);
    }

    @Override
    public TreeItem<String> visitUnitNode(UnitNode node) {
        TreeItem<String> item = new TreeItem<>("Unit");
        if (node.left != null) item.getChildren().add(node.left.accept(this));
        if (node.right != null) item.getChildren().add(node.right.accept(this));
        return item;
    }

    @Override
    public TreeItem<String> visitBaseUnitNode(BaseUnitNode node) {
        return new TreeItem<>("BaseUnit: " + node.symbol);
    }

    @Override
    public TreeItem<String> visitIfNode(IfNode node) {
        TreeItem<String> item = new TreeItem<>("If-Else");
        if (node.condition != null) {
            TreeItem<String> cond = new TreeItem<>("Condition");
            cond.getChildren().add(node.condition.accept(this));
            item.getChildren().add(cond);
        }
        if (node.thenBranch != null) {
            TreeItem<String> thenB = new TreeItem<>("Then");
            thenB.getChildren().add(node.thenBranch.accept(this));
            item.getChildren().add(thenB);
        }
        if (node.elseBranch != null) {
            TreeItem<String> elseB = new TreeItem<>("Else");
            elseB.getChildren().add(node.elseBranch.accept(this));
            item.getChildren().add(elseB);
        }
        return item;
    }
}