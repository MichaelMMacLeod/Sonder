package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Part extends Poly {
    private ArrayList<Part> children;
    private Part parent;

    public Part(Part parent, double x, double y, Color color) {
        super(
                new double[] {-30,  30, 30, -30},
                new double[] {-30, -30, 30,  30},
                x,
                y,
                0,
                0,
                color,
                false);
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
        children = new ArrayList<>();
    }

    private void orphan(Part child) {
        children.remove(child);
    }

    public void detatch() {
        parent.orphan(this);
        parent = null;
    }

    @Override
    public void translate(double dx, double dy) {
        super.translate(dx, dy);
        System.out.println(new Point2D.Double(getX(), getY()));
        for (Part child : children) {
            child.translate(dx, dy);
        }
    }

    public ArrayList<Part> getParts() {
        ArrayList<Part> parts = new ArrayList<>();
        parts.add(this);

        if (children.size() == 0) {
            return parts;
        }

        for (Part child : children) {
            parts.addAll(child.getParts());
        }

        return parts;
    }

    private void addChild(Part part) {
        children.add(part);
    }
}
