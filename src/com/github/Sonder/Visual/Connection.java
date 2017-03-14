package com.github.Sonder.Visual;

public abstract class Connection extends Moveable {
    private Outline source;
    private Outline reference;

    private Connection(double ax, double ay, Outline source) {
        super(ax, ay);

        this.source = source;
    }

    public final void setReference(Outline reference) {
        this.reference = reference;
    }

    public static class Parent extends Connection {
        public Parent(double ax, double ay, Outline source) {
            super(ax, ay, source);
        }
    }

    public static class Child extends Connection {
        public Child(double ax, double ay, Outline source) {
            super(ax, ay, source);
        }
    }
}
