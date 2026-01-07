package com.company.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




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

//public class Main extends Application {
//
//    @Override
//    public void start(Stage stage) {
//
//        TextArea textArea = new TextArea();
//        textArea.setPromptText("Enter the equation:");
//
//        Button checkButton = new Button("Check");
//
//        checkButton.setOnAction(e -> {
//            String input = textArea.getText();
//            System.out.println("User input: " + input);
//        });
//
//        VBox root = new VBox(10, textArea, checkButton);
//        root.setStyle("-fx-padding: 15;");
//
//        Scene scene = new Scene(root, 600, 400);
//
//        stage.setTitle("Math DSL");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}


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