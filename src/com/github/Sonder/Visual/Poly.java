package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Poly {
    private Poly parent;
    private Poly[] children;
    private int nchildren;
    private int npolys;

    private final Point2D.Double connector;
    public Point2D.Double getConnector() {
        return new Point2D.Double(connector.x, connector.y);
    }

    private final Point2D.Double[] nodes;
    private final double[] nodeRotations;
    public Point2D.Double[] getNodes() {
        return Transform.copy(nodes);
    }
    public double[] getNodeRotations() {
        return Arrays.copyOf(nodeRotations, nodeRotations.length);
    }

    private final Point2D.Double[] points;
    public Point2D.Double[] getPoints() {
        return Transform.copy(points);
    }

    private final Point2D.Double center;
    public Point2D.Double getCenter() {
        return new Point2D.Double(center.x, center.y);
    }

    private double rotation;
    public double getRotation() {
        return rotation;
    }

    private Color outline;
    private Color fill;
    public Color getOutline() {
        return outline;
    }
    public Color getFill() {
        return fill;
    }

    public Poly(
            Point2D.Double[] points,
            Point2D.Double center,
            Point2D.Double location,
            Color outline,
            Color fill,
            Point2D.Double[] nodes,
            double[] nodeRotations,
            Point2D.Double connector) {
        this.points = Transform.copy(points);
        this.center = new Point2D.Double(center.x, center.y);
        this.outline = outline;
        this.fill = fill;
        this.nodes = Transform.copy(nodes);
        this.nodeRotations = Arrays.copyOf(nodeRotations, nodeRotations.length);
        this.connector = new Point2D.Double(connector.x, connector.y);

        parent = null;
        children = new Poly[nodes.length];
        npolys = 1;

        moveTo(location);
    }

    private Poly getParent() {
        return parent;
    }

    public static void createRelationship(Poly parent, int node, Poly child) {
        parent.setChild(child, node);
        child.setParent(parent);
    }

    public static void removeRelationship(Poly parent, Poly child) {
        if (child.getParent() == parent) {
            parent.setChild(null, parent.getNode(child));
            child.setParent(null);
        }
    }

    private boolean hasChild(Poly child) {
        if (this == child)
            return true;

        boolean hasChild = false;
        for (Poly p : children) {
            if (p != null && p.hasChild(child)) {
                hasChild = true;
                break;
            }
        }

        return hasChild;
    }

    private int getNode(Poly child) {
        for (int i = 0; i < children.length; i++) {
            if (children[i] == child) {
                return i;
            }
        }

        return -1; // child is not actually a child
    }

    public void orphan() {
        if (parent != null) {
            parent.setChild(null, parent.getNode(this));
        }
    }

    public static void relate(Poly parent, int node, Poly child) {
        if (!parent.hasChild(child)) {
            parent.setChild(child, node);
            child.setParent(parent);
        }
    }

    private void setChild(Poly child, int node) {
        if (node >= 0 && node <= nchildren && !hasChild(child)) {
            if (children[node] != null) {
                if (child != null) {
                    npolys++;
                } else {
                    npolys--;
                }
            }
            children[node] = child;
        }
    }

    private void setParent(Poly parent) {
        this.parent = parent;
    }

    public void moveTo(Point2D.Double point) {
        double dx = point.x - center.x;
        double dy = point.y - center.y;

        Transform.translate(dx, dy, center);
        Transform.translate(dx, dy, connector);
        Transform.translate(dx, dy, points);
        Transform.translate(dx, dy, nodes);
    }

    public void translate(double dx, double dy) {
        Transform.translate(dx, dy, center);
        Transform.translate(dx, dy, connector);
        Transform.translate(dx, dy, points);
        Transform.translate(dx, dy, nodes);

        for (Poly child : children) {
            if (child != null) {
                child.translate(dx, dy);
            }
        }
    }

    public void rotate(double theta, double x, double y) {
        Transform.translate(-x, -y, center);
        Transform.translate(-x, -y, connector);
        Transform.translate(-x, -y, points);
        Transform.translate(-x, -y, nodes);

        Transform.rotate(theta, center);
        Transform.rotate(theta, connector);
        Transform.rotate(theta, points);
        Transform.rotate(theta, nodes);

        Transform.translate(x, y, center);
        Transform.translate(x, y, connector);
        Transform.translate(x, y, points);
        Transform.translate(x, y, nodes);

        for (Poly child : children) {
            if (child != null) {
                child.rotate(theta, x, y);
            }
        }
    }

    public boolean contains(double x, double y) {
        int[] xPointsPrime = new int[points.length];
        int[] yPointsPrime = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPointsPrime[i] = (int) points[i].x;
            yPointsPrime[i] = (int) points[i].y;
        }

        Polygon p = new Polygon(xPointsPrime, yPointsPrime, points.length);

        return p.contains(x, y);
    }

    public boolean contains(Poly other) {
        Point2D.Double[] otherPoints = other.getPoints();

        int[] otherXPointsPrime = new int[otherPoints.length];
        int[] otherYPointsPrime = new int[otherPoints.length];

        for (int i = 0; i < otherPoints.length; i++) {
            otherXPointsPrime[i] = (int) otherPoints[i].x;
            otherYPointsPrime[i] = (int) otherPoints[i].y;
        }

        int[] xPointsPrime = new int[points.length];
        int[] yPointsPrime = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPointsPrime[i] = (int) points[i].x;
            yPointsPrime[i] = (int) points[i].y;
        }

        Area area = new Area(new Polygon(xPointsPrime, yPointsPrime, points.length));
        Area otherArea = new Area(new Polygon(otherXPointsPrime, otherYPointsPrime, otherPoints.length));

        area.intersect(otherArea);

        return !area.isEmpty();
    }
}