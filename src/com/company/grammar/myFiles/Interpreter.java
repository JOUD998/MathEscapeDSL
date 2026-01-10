package com.company.grammar.myFiles;

public class Interpreter {

    private Context ctx;

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

    public int evaluation(Node node) {
        if (node instanceof IntNode) {
            return ((IntNode) node).value;
        }
        else if (node instanceof IdNode) {
            String name = ((IdNode) node).name;
            if (!ctx.contains(name)) {
                throw new RuntimeException("Variable not defined: " + name);
            }
            return ctx.get(name);
        }

        else if (node instanceof FactorialNode) {
            int value = evaluation(((FactorialNode) node).child);
            return factorial(value);
        }


        else if (node instanceof BinaryOpNode) {
            BinaryOpNode bin = (BinaryOpNode) node;

            if (bin.op.equals("=")) {
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
                case "+": return left + right;
                case "-": return left - right;
                case "*": return left * right;
                case "/": return left / right;
                default:
                    throw new RuntimeException("Unknown operator: " + bin.op);
            }
        }

        throw new RuntimeException("Unknown node type");
    }
}