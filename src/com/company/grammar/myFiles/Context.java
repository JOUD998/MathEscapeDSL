package com.company.grammar.myFiles;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private Map<String, Integer> vars = new HashMap<>();

    public void set(String name, int value) {
        vars.put(name, value);
    }

    public int get(String name) {
        System.out.println("The name:" + name);
        System.out.println("Vars:" + vars);
        if (!vars.containsKey(name)) {
            throw new RuntimeException("Variable not defined: " + name);
        }
         return vars.get(name);
    }
    public boolean contains(String name) {
        return vars.containsKey(name);
    }

}
