package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Part extends Poly {
    private ArrayList<Part> children;
    private Part parent;

    Point2D.Double vector;

    /**
     * Constructs a Part with no parent.
     *
     * @param vector   is the initial direction and speed of the Part.
     * @param xverts   are the x vertices of the Part's polygon.
     * @param yverts   are the y vertices of the Part's polygon.
     * @param x        is the x location of the centroid of the polygon in space.
     * @param y        is the y location of the centroid of the polygon in space.
     * @param color    is the color of the polygon.
     * @param isFilled is true if the polygon should be colored in.
     */
    public Part(
            Point2D.Double vector,
            double[] xverts,
            double[] yverts,
            double x,
            double y,
            Color color,
            boolean isFilled) {
        super(xverts, yverts, x, y, 0, 0, color, isFilled);

        this.parent = null;

        this.vector = vector;

        children = new ArrayList<>();
    }

    /**
     * Constructs a Part with a parent.
     *
     * @param parent   is the parent Part.
     * @param xverts   are the x vertices of the Part's polygon.
     * @param yverts   are the y vertices of the Part's polygon.
     * @param x        is the x location of the centroid of the polygon in space.
     * @param y        is the y location of the centroid of the polygon in space.
     * @param color    is the color of the polygon.
     * @param isFilled is true if the polygon should be colored in.
     */
    public Part(
            Part parent,
            double[] xverts,
            double[] yverts,
            double x,
            double y,
            Color color,
            boolean isFilled) {
        super(xverts, yverts, x, y, 0, 0, color, isFilled);

        this.parent = parent;
        this.parent.addChild(this);

        this.vector = parent.getVector();

        children = new ArrayList<>();
    }

    public void thrust() {
        for (Part child : children) {
            child.thrust();
        }
    }

    public void update() {
        translate(vector.x, vector.y);
    }

    public Point2D.Double getVector() {
        return vector;
    }

    @Override
    public void rotate(double theta, double x, double y) {
        super.rotate(theta, x, y);
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
        vector = new Point2D.Double(vector.x, vector.y);
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
