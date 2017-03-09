package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

public class Poly {
    public void thrust(double force) {
        vector.x += force * Math.cos(rotation);
        vector.y += force * Math.sin(rotation);
    }

    public void rotate(double theta) {
        rotate(theta, getCenterX(), getCenterY());
    }

    public void update() {
        translate(vector.x, vector.y);
    }

    private final Node[] nodes;
    private Node origin;

    private Point2D.Double vector;

    private void setVector(Point2D.Double vector) {
        this.vector = vector;

        for (Node n : nodes) {
            if (n.poly != null) {
                n.poly.setVector(vector);
            }
        }
    }
    private Point2D.Double getVector() {
        return vector;
    }

    private void setOrigin(Node origin) {
        this.origin = origin;
    }

    /**
     * The connector is where this Poly connects to other Polys.
     */

    private final Point2D.Double connector;

    public double getXConnector() {
        return connector.x;
    }

    public double getYConnector() {
        return connector.y;
    }

    public double[] getXNodes() {
        double[] xNodes = new double[nodes.length];
        for (int i = 0; i < xNodes.length; i++) {
            xNodes[i] = nodes[i].point.x;
        }
        return xNodes;
    }

    public double[] getYNodes() {
        double[] yNodes = new double[nodes.length];
        for (int i = 0; i < yNodes.length; i++) {
            yNodes[i] = nodes[i].point.y;
        }
        return yNodes;
    }

    public int getNumberOfNodes() {
        return nodes.length;
    }

    /**
     * The points defining the shape of the Poly
     */
    private final Point2D.Double[] vertices;

    public double[] getXVertices() {
        double[] xVertices = new double[vertices.length];
        for (int i = 0; i < xVertices.length; i++) {
            xVertices[i] = vertices[i].x;
        }
        return xVertices;
    }

    public double[] getYVertices() {
        double[] yVertices = new double[vertices.length];
        for (int i = 0; i < yVertices.length; i++) {
            yVertices[i] = vertices[i].y;
        }
        return yVertices;
    }

    public int getNumberOfVertices() {
        return vertices.length;
    }

    /**
     * The center of the Poly. It is defined when the object is created.
     */
    private final Point2D.Double center;

    public double getCenterX() {
        return center.x;
    }

    public double getCenterY() {
        return center.y;
    }

    /**
     * The rotation of the Poly in radians. A polygon has 0 rotation when created.
     */
    private double rotation;

    private double getRotation() {
        return rotation;
    }

    /**
     * The color of the Poly.
     */
    private Color outline;
    private Color fill;

    public Color getOutline() {
        return outline;
    }

    public Color getFill() {
        return fill;
    }

    private void setFill(Color color) {
        this.fill = color;
        for (Node node : nodes) {
            if (node.poly != null) {
                node.poly.setFill(color);
            }
        }
    }

    /**
     * Creates a Polygon.
     *
     * @param xVerts        are the x points defining the shape of the Poly.
     * @param yVerts        are the y points defining the shape of the Poly.
     * @param verts         is the number of vertices.
     * @param cx            is the x coordinate of the center of the Poly.
     * @param cy            is the y coordinate of the center of the Poly.
     * @param x             is the x coordinate of the location of the Poly.
     * @param y             is the y coordinate of the location of the Poly.
     * @param outline       is the color of the outline of the Poly.
     * @param fill          is the color of the inside of the Poly.
     * @param xNodes        are the x points which other shapes can connect to.
     * @param yNodes        are the y points which other shapes can connect to.
     * @param nodeRotations are the rotations that connected parts have.
     * @param nodes         is the number of nodes.
     * @param xconnector    is the x coordinate of the point where this Poly connects to other Polys.
     * @param yconnector    is the y coordinate of the point where this Poly connects to other Polys.
     */
    Poly(
            double[] xVerts,
            double[] yVerts,
            int verts,
            double cx,
            double cy,
            double x,
            double y,
            Color outline,
            Color fill,
            double[] xNodes,
            double[] yNodes,
            double[] nodeRotations,
            int nodes,
            double xconnector,
            double yconnector) {
        vertices = new Point2D.Double[verts];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Point2D.Double(xVerts[i], yVerts[i]);
        }
        center = new Point2D.Double(cx, cy);
        this.outline = outline;

        this.fill = fill;

        connector = new Point2D.Double(xconnector, yconnector);

        this.nodes = new Node[nodes];
        for (int i = 0; i < this.nodes.length; i++) {
            this.nodes[i] = new Node(this, new Point2D.Double(xNodes[i], yNodes[i]), nodeRotations[i]);
        }

        origin = null;

        vector = new Point2D.Double(0, 0);

        moveTo(x, y);
    }

    public void attatch(Poly poly, int node) {
        nodes[node].attach(poly);
    }

    public void detatch() {
        if (origin != null) {
            origin.detach();
        }
    }

    private void moveTo(double x, double y) {
        double dx = x - center.x;
        double dy = y - center.y;

        Transform.translate(dx, dy, center);
        Transform.translate(dx, dy, connector);
        Transform.translate(dx, dy, vertices);

        for (Node n : nodes) {
            Transform.translate(dx, dy, n.point);
            if (n.poly != null) {
                n.poly.translate(dx, dy);
            }
        }
    }

    public void translate(double dx, double dy) {
        Transform.translate(dx, dy, center);
        Transform.translate(dx, dy, connector);
        Transform.translate(dx, dy, vertices);

        for (Node n : nodes) {
            Transform.translate(dx, dy, n.point);
        }
    }

    private void rotate(double theta, double x, double y) {
        rotation += theta;

        Transform.rotate(theta, x, y, center);
        Transform.rotate(theta, x, y, connector);
        Transform.rotate(theta, x, y, vertices);

        for (Node n : nodes) {
            n.rotation += theta;
            Transform.rotate(theta, x, y, n.point);
            if (n.poly != null) {
                n.poly.rotate(theta, x, y);
            }
        }
    }

    public boolean contains(double x, double y) {
        int[] xvertsPrime = new int[vertices.length];
        int[] yvertsPrime = new int[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            xvertsPrime[i] = (int) vertices[i].x;
            yvertsPrime[i] = (int) vertices[i].y;
        }

        Polygon p = new Polygon(xvertsPrime, yvertsPrime, vertices.length);

        return p.contains(x, y);
    }

    public boolean contains(Poly other) {
        double[] otherx = other.getXVertices();
        double[] othery = other.getYVertices();

        int othernv = other.getNumberOfVertices();

        int[] iotherx = new int[othernv];
        int[] iothery = new int[othernv];

        for (int i = 0; i < othernv; i++) {
            iotherx[i] = (int) otherx[i];
            iothery[i] = (int) othery[i];
        }

        int[] ixverts = new int[vertices.length];
        int[] iyverts = new int[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            ixverts[i] = (int) vertices[i].x;
            iyverts[i] = (int) vertices[i].y;
        }

        Area area = new Area(new Polygon(ixverts, iyverts, vertices.length));
        Area otherArea = new Area(new Polygon(iotherx, iothery, othernv));

        area.intersect(otherArea);

        return !area.isEmpty();
    }

    public boolean hasPoly(Poly poly) {
        if (this == poly)
            return true;

        boolean polyInNodes = false;
        for (Node n : nodes) {
            if (n.poly != null) {
                if (n.poly.hasPoly(poly)) {
                    polyInNodes = true;
                    break;
                }
            }
        }

        return polyInNodes;
    }

    private static class Transform {
        static void translate(double dx, double dy, Point2D.Double... points) {
            for (Point2D.Double point : points) {
                point.x += dx;
                point.y += dy;
            }
        }

        static void rotate(double theta, double x, double y, Point2D.Double... points) {
            double cos = Math.cos(theta), sin = Math.sin(theta);

            for (Point2D.Double point : points) {
                point.x -= x;
                point.y -= y;

                double xPrime = point.x * cos - point.y * sin;
                double yPrime = point.x * sin + point.y * cos;

                point.x = xPrime + x;
                point.y = yPrime + y;
            }
        }
    }

    private class Node {
        private final Poly source;
        private Poly poly;
        private final Point2D.Double point;
        private double rotation;

        Node(Poly source, Point2D.Double point, double rotation) {
            this.source = source;
            this.point = point;
            this.rotation = rotation;
        }

        void detach() {
            if (poly != null) {
                poly.setOrigin(null);
                poly.setVector(new Point2D.Double(poly.getVector().x, poly.getVector().y));
                poly = null;
            }
        }

        void attach(Poly poly) {
            if (this.poly == null) {
                poly.translate(point.x - poly.getXConnector(), point.y - poly.getYConnector());
                poly.rotate(rotation - poly.getRotation(), poly.getXConnector(), poly.getYConnector());
                poly.setOrigin(this);
                poly.setVector(source.getVector());
                this.poly = poly;
            }
        }
    }
}