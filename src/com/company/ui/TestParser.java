package com.company.ui;

import com.company.core.SyntaxErrorListener;
import com.company.core.ast.ASTBuilder;
import com.company.core.model.ASTNode;
import com.company.core.semantic.SemanticAnalyzer;
import com.company.grammar.MathDSLLexer;
import com.company.grammar.MathDSLParser;

import org.antlr.v4.runtime.*;

public class TestParser {

    public static void main(String[] args) throws Exception {



        String input = "let a:m = 5;";

        System.out.println("INPUT:");
        System.out.println(input);
        System.out.println("-------------");

        try {
            // 1️⃣ Lexer
            CharStream charStream = CharStreams.fromString(input);
            MathDSLLexer lexer = new MathDSLLexer(charStream);

            // إضافة SyntaxErrorListener
            lexer.removeErrorListeners();
            lexer.addErrorListener(new SyntaxErrorListener());

            // 2️⃣ Tokens
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 3️⃣ Parser
            MathDSLParser parser = new MathDSLParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new SyntaxErrorListener());

            // BailErrorStrategy لإيقاف parsing عند أول خطأ
            parser.setErrorHandler(new BailErrorStrategy());

            // Parsing
            MathDSLParser.ProgContext tree = parser.prog();
            System.out.println("Parsing done.");
            System.out.println("-------------");

            // 4️⃣ AST
            ASTBuilder astBuilder = new ASTBuilder();
            ASTNode ast = astBuilder.visitProg(tree);
            System.out.println("AST built.");
            System.out.println("-------------");

            // 5️⃣ Semantic Analysis
            SemanticAnalyzer analyzer = new SemanticAnalyzer();
            ast.accept(analyzer);

            System.out.println("-------------");
            System.out.println("Symbol table after analysis:");
            analyzer.currentScope.printTree();
            System.out.println(analyzer.currentScope.getSymbols().size());

        } catch (RuntimeException e) {
            System.err.println("Parsing failed due to syntax error:");
            System.err.println(e.getMessage());
        }

    }
}