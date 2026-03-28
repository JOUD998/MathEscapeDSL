package com.company.ui;

import com.company.core.ast.ASTBuilder;
import com.company.core.ast.ASTNode;
import com.company.core.engine.EvaluationResult;
import com.company.core.engine.Interpreter;
import com.company.core.semantic.SemanticAnalyzer;
import com.company.core.semantic.TermRewriter;
import com.company.grammar.MathDSLLexer;
import com.company.grammar.MathDSLParser;
import org.antlr.v4.runtime.*;
import java.util.Scanner;

public class REPL {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Math-DSL Interactive REPL ===");
        System.out.println("Type 'exit' to quit, 'clear' to reset scope.");
        System.out.println("---------------------------------");

        // نحتفظ بـ analyzer واحد خارج الـ loop للحفاظ على الرموز (Variables)
        SemanticAnalyzer analyzer = new SemanticAnalyzer();

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) break;
            if (input.trim().isEmpty()) continue;
            if (input.equalsIgnoreCase("clear")) {
                analyzer = new SemanticAnalyzer();
                System.out.println("Scope cleared.");
                continue;
            }

            if (!input.endsWith(";")) input += ";";

            try {
                // 1️⃣ Lexer & Tokens
                CharStream charStream = CharStreams.fromString(input);
                MathDSLLexer lexer = new MathDSLLexer(charStream);
                CommonTokenStream tokens = new CommonTokenStream(lexer);

                // 2️⃣ Parser
                MathDSLParser parser = new MathDSLParser(tokens);
                MathDSLParser.ProgContext tree = parser.prog();

                // 3️⃣ AST Builder
                ASTBuilder astBuilder = new ASTBuilder();
                ASTNode ast = astBuilder.visitProg(tree);
                System.out.println("AST (JSON): " + ast.toJson());
                // 4️⃣ Semantic Analysis
                ast.accept(analyzer);

                // 5️⃣ Term Rewriter (Optimization)
                TermRewriter termRewriter = new TermRewriter(analyzer.currentScope);
                ASTNode simplifiedAst = ast.accept(termRewriter);
                System.out.println("Optimized AST (JSON): " + simplifiedAst.toJson());
                analyzer.currentScope.printTree();
                // 6️⃣ Interpreter (Execution) 🚀
                Interpreter interpreter = new Interpreter(analyzer.currentScope);
                EvaluationResult finalResult = simplifiedAst.accept(interpreter);
//                System.out.println("---------------------------------");
//                System.out.println("AST (JSON): " + simplifiedAst.toJson());
//                System.out.println("---------------------------------");

                // 7️⃣ Display Results
                if (finalResult != null) {
                    System.out.println("Interpreter: ");
                    System.out.println("Value: " + finalResult.value());
                    System.out.println("Unit:  [" + finalResult.dimension().toBaseUnitString() + "]");
                    System.out.println("---------------------------------");
                }


            } catch (Exception e) {
                System.err.println("❌Error: " + e.getMessage());
//                e.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
        //let t:s= 2000 ms