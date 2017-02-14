package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Part extends Poly {
    private ArrayList<Part> children;
    private Part parent;

    public Part(Part parent, double x, double y, Color color, boolean isFilled) {
        super(
                new double[] {-30,  30, 30, -30},
                new double[] {-30, -30, 30,  30},
                x,
                y,
                0,
                0,
                color,
                isFilled);
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
        children = new ArrayList<>();
    }

    public void rotate(double theta) {
        Point2D.Double anchorPoint = anchorPoint();
        rotate(theta, anchorPoint.x, anchorPoint.y);
    }

    private void rotate(double theta, double x, double y) {
        setAnchor(x, y);
        super.rotate(theta);
        for (Part child : children) {
            child.rotate(theta, x, y);
        }
    }

    public Point2D.Double anchorPoint() {
        Point2D.Double thisAnchor = new Point2D.Double(getX(), getY());

        if (children.size() == 0) {
            return thisAnchor;
        }

        ArrayList<Point2D.Double> childAnchors = new ArrayList<>();
        for (Part child : children) {
            childAnchors.add(child.anchorPoint());
        }

        for (Point2D.Double childAnchor : childAnchors) {
            thisAnchor.x += childAnchor.x;
            thisAnchor.y += childAnchor.y;
        }
        thisAnchor.x /= (childAnchors.size() + 1);
        thisAnchor.y /= (childAnchors.size() + 1);

        return thisAnchor;
    }

    private void orphan(Part child) {
        children.remove(child);
    }

    public void detach() {
        parent.orphan(this);
        parent = null;
    }

    @Override
    public void translate(double dx, double dy) {
        super.translate(dx, dy);
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
