package com.company.core.interpreter;

import com.company.core.model.*;

public class Interpreter {

    private  Context ctx;

    int factorial(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }



    public Interpreter(Context ctx) {
        this.ctx = ctx;
    }

    public int evaluation(ASTNode astNode) {
        if (astNode instanceof IntNode) {
            return ((IntNode) astNode).value;
        }
        else if (astNode instanceof IdNode) {
            String name = ((IdNode) astNode).name;
            if (!ctx.contains(name)) {
                throw new RuntimeException("Variable not defined: " + name);
            }
            return ctx.get(name);
        }

        else if (astNode instanceof FactorialNode) {
            ASTNode base = ((FactorialNode) astNode).child;
            int value;

            if (base instanceof IntNode) {
                value = ((IntNode) base).value;
            } else if (base instanceof IdNode) {
                String name = ((IdNode) base).name;
                if (!ctx.contains(name)) {
                    throw new RuntimeException("Variable not defined");
                }
                value = ctx.get(name);
            } else {
                throw new RuntimeException("Factorial base is not an integer");
            }

            return factorial(value);
        }

        else if (astNode instanceof PowerNode) {
            int base = evaluation(((PowerNode) astNode).base);
            int exponent = evaluation(((PowerNode) astNode).exponent);
            return (int)Math.pow(base, exponent);
        }

        else if (astNode instanceof BinaryOpNode) {
            BinaryOpNode bin = (BinaryOpNode) astNode;

            if (bin.op == '=') {
                if (!(bin.left instanceof IdNode)) {
                    throw new RuntimeException("Assignment error: left side must be a variable");
                }
                int value = evaluation(bin.right);
                ctx.set(((IdNode) bin.left).name, value);
                return value;
            }

            int left = evaluation(bin.left);
            int right = evaluation(bin.right);
            switch (bin.op) {
                case '+': return left + right;
                case '-': return left - right;
                case '*': return left * right;
                case '/': return left / right;
                default:
                    throw new RuntimeException("Unknown operator: " + bin.op);
            }
        }

        throw new RuntimeException("Unknown astNode type");
    }
}