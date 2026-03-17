package com.company.core.semantic.unit;


public class Dimension {
    public int length;
    public int time;
    public int mass;

    public Dimension() {
        this.length = 0;
        this.time = 0;
        this.mass = 0;
    }

    public Dimension(int length, int time, int mass) {
        this.length = length;
        this.time = time;
        this.mass = mass;
    }

    public Dimension multiply(Dimension other) {
        return new Dimension(this.length + other.length, this.time + other.time, this.mass + other.mass);
    }
    public Dimension divide(Dimension other) {
        return new Dimension(this.length - other.length, this.time - other.time, this.mass - other.mass);
    }
    public boolean equals(Dimension other) {
        return this.length == other.length &&
                this.time == other.time &&
                this.mass == other.mass;
    }
    public Dimension copy() {
        return new Dimension(this.length, this.time, this.mass);
    }

    public String toReadableString() {

        if (length == 0 && time == 0 && mass == 0)
            return "NONE";

        StringBuilder sb = new StringBuilder();

        if (length != 0) {
            sb.append("LENGTH");
            if (length != 1) sb.append("^").append(length);
        }

        if (time != 0) {
            if (sb.length() > 0) sb.append(" * ");
            sb.append("TIME");
            if (time != 1) sb.append("^").append(time);
        }

        if (mass != 0) {
            if (sb.length() > 0) sb.append(" * ");
            sb.append("MASS");
            if (mass != 1) sb.append("^").append(mass);
        }

        return sb.toString();
    }

    public boolean isNone() {
        return length == 0 && time == 0 && mass == 0;
    }

    public Dimension scale(double factor) {
        return new Dimension(
                (int)(this.length * factor),
                (int)(this.time * factor),
                (int)(this.mass * factor)
        );
    }
    @Override
    public String toString() {
        return toReadableString();
    }

}

