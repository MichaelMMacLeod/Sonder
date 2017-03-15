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

        this.link = new Point2D.Double(link.x, link.y);
    }

    private Point2D.Double link;
    private Connection parent;
    private Connection[] children;

    public Connection[] getConnections() {
        return children;
    }

    public Point2D.Double[] getConnectionPoints() {
        Point2D.Double[] points = new Point2D.Double[children.length];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point2D.Double(children[i].getAX(), children[i].getAY());
        }
        return points;
    }

    public boolean hasInChain(Chain chain) {
        if (this == chain)
            return true;

        boolean inChain = false;

        for (Connection c : children) {
            Chain reference = c.getReference();
            if (reference != null && reference.hasInChain(chain)) {
                inChain = true;
                break;
            }
        }

        return inChain;
    }

    void setParent(Connection parent) {
        this.parent = parent;
    }

    public void detachFromParent() {
        parent = null;
    }

    public void attachChild(Connection connection, Chain chain) {
        for (Connection child : children) {
            if (child == connection) {
                child.attachReference(chain);
                break;
            }
        }
    }

    public Point2D.Double getLink() {
        return new Point2D.Double(link.x, link.y);
    }

    @Override
    public void transform() {
        super.transform();

        for (Connection c : children)
            c.transform();
    }

    @Override
    protected void setAnchor(double ax, double ay) {
        super.setAnchor(ax, ay);

        for (Connection c : children)
            c.setAnchor(ax, ay);
    }

    @Override
    public void translate(double dx, double dy) {
        super.translate(dx, dy);

        for (Connection c : children)
            c.translate(dx, dy);
    }

    @Override
    public void rotate(double dr) {
        super.rotate(dr);

        for (Connection c : children)
            c.rotate(dr);
    }

    @Override
    protected void translate() {
        super.translate();
        
        link.setLocation(link.x + getDX(), link.y + getDY());
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
    }
}
