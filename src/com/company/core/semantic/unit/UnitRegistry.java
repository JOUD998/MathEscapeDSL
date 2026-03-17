package com.company.core.semantic.unit;

import java.util.Map;

public class UnitRegistry {

    public static final Map<String, UnitInfo> UNIT_TABLE = Map.of(

            "cm", new UnitInfo(new Dimension(1, 0, 0), 0.01),
            "m", new UnitInfo(new Dimension(1, 0, 0), 1.0),
            "km", new UnitInfo(new Dimension(1, 0, 0), 1000.0),


            "ms", new UnitInfo(new Dimension(0, 1, 0), 0.001),
            "s", new UnitInfo(new Dimension(0, 1, 0), 1.0),
            "h", new UnitInfo(new Dimension(0, 1, 0), 3600.0),


            "g", new UnitInfo(new Dimension(0, 0, 1), 1.0),
            "kg", new UnitInfo(new Dimension(0, 0, 1), 1000.0),

            "m/s", new UnitInfo(new Dimension(1, -1, 0), 1.0),
            "km/h", new UnitInfo(new Dimension(1, -1, 0), 1000.0 / 3600.0)

    );

    public static boolean containsDimension(Dimension dim) {

        for (UnitInfo info : UNIT_TABLE.values()) {
            if (info.getDimension().equals(dim)) {
                return true;
            }
        }

        return false;
    }
    public static String getBaseUnitSymbol(Dimension dim) {
        if (dim.length == 1 && dim.time == 0 && dim.mass == 0) return "m";
        if (dim.length == 0 && dim.time == 1 && dim.mass == 0) return "s";
        if (dim.length == 0 && dim.time == 0 && dim.mass == 1) return "g";
        if (dim.length == 1 && dim.time == -1 && dim.mass == 0) return "m/s";
        return "NONE";
    }


}
