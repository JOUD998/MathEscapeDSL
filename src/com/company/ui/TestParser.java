package com.company.ui;
import com.company.core.ast.ASTBuilder;
import com.company.core.model.ASTNode;
import com.company.core.model.NumberLiteralNode;
import com.company.core.model.unit.BaseUnitNode;
import com.company.core.model.unit.UnitNode;
import com.company.core.semantic.SemanticAnalyzer;
import com.company.core.symbol_table.SymbolTable;
import com.company.core.symbol_table.VariableSymbol;
import com.company.grammar.MathDSLLexer;
import com.company.grammar.MathDSLParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class TestParser {
    public static void main(String[] args) throws Exception {

        String input = "let x = 3m + 4m";

        // 1️⃣ إنشاء Lexer و Parser من ANTLR
        CharStream charStream = CharStreams.fromString(input);
        MathDSLLexer lexer = new MathDSLLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathDSLParser parser = new MathDSLParser(tokens);

        // 2️⃣ عمل Parse للشجرة
        MathDSLParser.ProgContext tree = parser.prog();

        // 3️⃣ بناء AST باستخدام ASTBuilder
        ASTBuilder astBuilder = new ASTBuilder();
        ASTNode ast = astBuilder.visitProg(tree);


        // 4️⃣ تطبيق SemanticAnalyzer
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        ast.accept(analyzer); // هنا رح يزور AST كله
        System.out.println("Semantic analysis done!");
        System.out.println("declared variables: " );
    }

}