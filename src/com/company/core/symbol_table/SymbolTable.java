package com.company.core.symbol_table;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private Map<String, Symbol> symbols = new HashMap<>();
    private SymbolTable parent;

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    public void define(Symbol symbol) {
        symbols.put(symbol.getName(), symbol);
    }

    //Lexical Scope Resolution
    public Symbol resolve(String name) {

        Symbol symbol = symbols.get(name);

        if (symbol != null) {
            return symbol;
        }

        if (parent != null) {
            return parent.resolve(name);
        }

        return null;
    }

    public SymbolTable getParent() {
        return parent;
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public boolean existsInCurrentScope(String name) {
        return symbols.containsKey(name);
    }

    public void remove(String name) {
        symbols.remove(name);
    }

    public void printTree() {
        printTree("", true);
    }

    private void printTree(String prefix, boolean isRoot) {
        if (isRoot) {
            System.out.println("SymbolTable (root/global scope):");
        } else {
            System.out.println(prefix + "└─ Scope:");
        }

        String childPrefix = prefix + "    ";
        for (Map.Entry<String, Symbol> entry : symbols.entrySet()) {
            Symbol sym = entry.getValue();
            if (sym instanceof VariableSymbol) {
                VariableSymbol vs = (VariableSymbol) sym;
                System.out.println(childPrefix + "├─ " + vs.getName() + " : " + vs.getDimension().toString());
            } else if (sym instanceof FunctionSymbol) {
                FunctionSymbol fs = (FunctionSymbol) sym;
                System.out.println(childPrefix + "├─ " + fs.getName() + " : Function");

                // اطبع الـ scope الخاص بالدالة (parameters + أي متغيرات جواتها)
                if (fs.getScope() != null) {
                    fs.getScope().printTree(childPrefix, false);
                }
            } else {
                System.out.println(childPrefix + "├─ " + sym.getName() + " : " + sym.getClass().getSimpleName());
            }
        }
    }



}