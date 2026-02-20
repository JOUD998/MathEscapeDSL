package com.company.core.realTime;

public class ErrorHandling {

    public static String simplifyMessage(String rawMsg) {
        if (rawMsg == null) return "Unknown error";

        if (rawMsg.contains("Variable not defined")) {
            return "Variable is not defined! Please define it first.";
        } else if (rawMsg.contains("Assignment error")) {
            return "Assignment error! Left side must be a variable.";
        } else if (rawMsg.contains("Cannot invoke") && rawMsg.contains("ID()")) {
            return "There is an error in the variable name or equation syntax.";
        } else if (rawMsg.contains("Unknown operator")) {
            return "Unsupported operation. Use +, -, *, /, or ! only.";
        } else if (rawMsg.contains("Factorial base is not an integer")) {
            return "Factorial (!) must be applied to an integer only.";
        }

        return "There is a syntax error: " + rawMsg;
    }


}