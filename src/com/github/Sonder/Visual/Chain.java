package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

public abstract class Chain extends Outline {
    Chain(double ax, double ay, double r, Point2D.Double[] points,
                 Point2D.Double[] childPoints,
                 double[] childRotations,
                 Point2D.Double link) {
        super(ax, ay, r, points);

        children = new Connection[childPoints.length];
        for (int i = 0; i < children.length; i++) {
            children[i] = new Connection(childPoints[i].x, childPoints[i].y, childRotations[i], this);
        }

        link = new Point2D.Double(link.x, link.y);
    }

    private Point2D.Double link;
    private Connection parent;
    private Connection[] children;

    void setParent(Connection parent) {
        this.parent = parent;
    }

    void detachFromParent() {
        parent = null;
    }

    double getLinkX() {
        return link.x;
    }

    double getLinkY() {
        return link.y;
    }

    @Override
    protected void setAnchor(double ax, double ay) {
        super.setAnchor(ax, ay);

        for (Connection c : children)
            c.setAnchor(ax, ay);
    }

    @Override
    protected void translate(double dx, double dy) {
        super.translate(dx, dy);

        for (Connection c : children)
            c.translate(dx, dy);
    }

    @Override
    protected void rotate(double dr) {
        super.rotate(dr);

        for (Connection c : children)
            c.rotate(dr);
    }

    @Override
    protected void translate() {
        super.translate();

        link.setLocation(link.x + getDX(), link.y + getDY());

        for (Connection c : children)
            c.translate();
    }

    @Override
    protected void rotate() {
        super.rotate();

        double cos = Math.cos(getDR()), sin = Math.sin(getDR());
        Point2D.Double prime = new Point2D.Double(
                link.x - getAX(),
                link.y - getAY());
        link.setLocation(
                prime.x * cos - prime.y * sin + getAX(),
                prime.x * sin + prime.y * cos + getAY());

        for (Connection c : children)
            c.rotate();
    }
}
