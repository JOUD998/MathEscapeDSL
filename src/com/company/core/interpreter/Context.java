package com.company.core.interpreter;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private Map<String, Integer> vars = new HashMap<>();

    public void set(String name, int value) {
        vars.put(name, value);
    }

    public int get(String name) {
        if (!vars.containsKey(name)) {
            throw new RuntimeException("Variable not defined: " + name);
        }
        System.out.println("The name:" + name);
        System.out.println("Vars:" + vars);

         return vars.get(name);
    }

    public boolean contains(String name) {
        return vars.containsKey(name);
    }

}
