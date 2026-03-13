package com.company.core.model;

import com.company.core.semantic.ASTVisitor;
import com.company.core.semantic.unit.Dimension;
public abstract class ASTNode {

    public abstract <T> T accept(ASTVisitor<T> visitor);
//    public DimensionEnum dimensionEnum = DimensionEnum.NONE;
    public Dimension dimension = new Dimension();
    public double toBaseFactor = 1.0;
    public abstract String toJson();

}
