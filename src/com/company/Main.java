package com.company;

import com.company.grammar.generated.MathEscapeLexer;
import com.company.grammar.generated.MathEscapeParser;
import com.company.grammar.myFiles.MyMathVisitor;
import com.company.grammar.myFiles.Node;
import grammar.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

    public static void main(String[] args) {

        String input = "8 = x + 2";
        MathEscapeLexer lexer = new MathEscapeLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathEscapeParser parser = new MathEscapeParser(tokens);
        MathEscapeParser.MathEscapeContext tree = parser.mathEscape();
        MyMathVisitor visitor = new MyMathVisitor();
        Node result = visitor.visit(tree);

        System.out.println("\u001B[32m" + result.toJson() + "\u001B[0m");
    }
}
