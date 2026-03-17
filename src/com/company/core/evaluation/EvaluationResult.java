package com.company.core.evaluation;

import com.company.core.semantic.unit.Dimension;

public record EvaluationResult(double value, Dimension dimension) {

    @Override
    public String toString() {
        return value + " [" + dimension.toReadableString() + "]";
    }

}
