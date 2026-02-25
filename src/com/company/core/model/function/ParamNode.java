package com.company.core.model.function;

import com.company.core.model.ASTNode;
import com.company.core.model.unit.UnitNode;

public class ParamNode extends ASTNode {

    public String name;
    public UnitNode unit;

    public ParamNode(String name){
        this.name = name;
    }
    public ParamNode(String name, UnitNode unit){
        this.name = name;
        this.unit = unit;
    }

    @Override
    public String toJson() {
        return "{ \"type\": \"Param\", \"name\": \"" + name + "\", \"unit\": " + (unit != null ? unit.toJson() : "null") + " }";
    }
}
