package com.company.ui;

import com.company.core.realTime.ErrorHandling;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private InterpreterFacade interpreterFacade = new InterpreterFacade();

    @Override
    public void start(Stage stage) {

        // Input area
        Label inputLabel = new Label("Enter your equation:");
        TextArea inputArea = new TextArea();
        inputArea.setPromptText("e.g. x = 8 + 2");
        inputArea.setPrefRowCount(3);

        // Output area
        Label outputLabel = new Label("Output:");
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(5);
        outputArea.setText("Output will appear here...");
        outputArea.setStyle("-fx-control-inner-background: #f0f0f0; -fx-font-family: monospace;");

        VBox root = new VBox(10, inputLabel, inputArea, outputLabel, outputArea);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f5f5f5;");

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Math DSL Real-Time");
        stage.setScene(scene);
        stage.show();

        inputArea.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isBlank()) {
                outputArea.setText("Output will appear here...");
                return;
            }

            try {
                int result = interpreterFacade.evaluate(newText);
                outputArea.setStyle("-fx-text-fill: green;");
                outputArea.setText("✅ Result: " + result);
            } catch (RuntimeException ex) {
                outputArea.setStyle("-fx-text-fill: red;");
                outputArea.setText("❌ Error: " + ErrorHandling.simplifyMessage(ex.getMessage()));
            } catch (Exception ex) {
                outputArea.setStyle("-fx-text-fill: red;");
                outputArea.setText("❌ Parsing error: " + ErrorHandling.simplifyMessage(ex.getMessage()));
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}