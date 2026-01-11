package com.company.ui;

import com.company.generated_Grammars.MathEscapeLexer;
import com.company.generated_Grammars.MathEscapeParser;
import com.company.core.interpreter.Context;
import com.company.core.interpreter.Interpreter;
import com.company.core.interpreter.MyMathVisitor;
import com.company.core.model.Node;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class InterpreterFacade {

    private Context ctx;
    private Interpreter interpreter;

    InterpreterFacade() {
         ctx = new Context();
         interpreter = new Interpreter(ctx);
    }

    public int evaluate(String input) {
        MathEscapeLexer lexer = new MathEscapeLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathEscapeParser parser = new MathEscapeParser(tokens);
        MathEscapeParser.MathEscapeContext tree = parser.mathEscape();

        MyMathVisitor visitor = new MyMathVisitor();
        Node ast = visitor.visit(tree);
        return interpreter.evaluation(ast);

    }


}