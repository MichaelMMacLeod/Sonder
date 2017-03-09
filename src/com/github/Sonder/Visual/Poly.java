package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Poly {
    private final Color defaultColor = new Color((int) (Math.random() * 0x1000000));

    private final Node[] nodes;
    private Node origin;

    private void setOrigin(Node origin) {
        this.origin = origin;
    }

    private Color getDefaultColor() {
        return defaultColor;
    }

    /**
     * The connector is where this Poly connects to other Polys.
     */

    private double xconnector;
    private double yconnector;

    public double getXConnector() {
        return xconnector;
    }

    public double getYConnector() {
        return yconnector;
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
    private final double[] xVerts;
    private final double[] yVerts;
    private final int verts;

    public double[] getXVertices() {
        return Arrays.copyOf(xVerts, verts);
    }

    public double[] getYVertices() {
        return Arrays.copyOf(yVerts, verts);
    }

    public int getNumberOfVertices() {
        return verts;
    }

    /**
     * The center of the Poly. It is defined when the object is created.
     */
    private double cx;
    private double cy;

    public double getCenterX() {
        return cx;
    }

    public double getCenterY() {
        return cy;
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
        this.xVerts = Arrays.copyOf(xVerts, verts);
        this.yVerts = Arrays.copyOf(yVerts, verts);
        this.verts = verts;
        this.cx = cx;
        this.cy = cy;
        this.outline = outline;

        this.fill = defaultColor;

        this.xconnector = xconnector;
        this.yconnector = yconnector;

        this.nodes = new Node[nodes];
        for (int i = 0; i < this.nodes.length; i++) {
            this.nodes[i] = new Node(this, new Point2D.Double(xNodes[i], yNodes[i]), nodeRotations[i]);
        }

        origin = null;

        moveTo(x, y);
    }

    public void attatch(Poly poly, int node) {
        nodes[node].attatch(poly);
    }

    public void detatch() {
        if (origin != null) {
            origin.detatch();
        }
    }

    private void moveTo(double x, double y) {
        double dx = x - cx;
        double dy = y - cy;

        cx += dx;
        cy += dy;

        xconnector += dx;
        yconnector += dy;

        for (int i = 0; i < verts; i++) {
            xVerts[i] += dx;
            yVerts[i] += dy;
        }

        for (Node n : nodes) {
            n.translate(dx, dy);
        }
    }

    public void translate(double dx, double dy) {
        cx += dx;
        cy += dy;

        xconnector += dx;
        yconnector += dy;

        for (int i = 0; i < verts; i++) {
            xVerts[i] += dx;
            yVerts[i] += dy;
        }

        for (Node n : nodes) {
            n.translate(dx, dy);
        }
    }

    private void rotate(double theta, double x, double y) {
        rotation += theta;

        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        cx -= x;
        cy -= y;

        xconnector -= x;
        yconnector -= y;

        for (int i = 0; i < verts; i++) {
            xVerts[i] -= x;
            yVerts[i] -= y;
        }

        double cxPrime;
        double cyPrime;

        double xconnectorPrime;
        double yconnectorPrime;

        double[] xvertsPrime = new double[verts];
        double[] yvertsPrime = new double[verts];

        cxPrime = cx * cos - cy * sin;
        cyPrime = cx * sin + cy * cos;

        xconnectorPrime = xconnector * cos - yconnector * sin;
        yconnectorPrime = xconnector * sin + yconnector * cos;

        for (int i = 0; i < verts; i++) {
            xvertsPrime[i] = xVerts[i] * cos - yVerts[i] * sin;
            yvertsPrime[i] = xVerts[i] * sin + yVerts[i] * cos;
        }

        cx = cxPrime + x;
        cy = cyPrime + y;

        xconnector = xconnectorPrime + x;
        yconnector = yconnectorPrime + y;

        for (int i = 0; i < verts; i++) {
            xVerts[i] = xvertsPrime[i] + x;
            yVerts[i] = yvertsPrime[i] + y;
        }

        for (Node n : nodes) {
            n.rotate(theta, x, y);
        }
    }

    public boolean contains(double x, double y) {
        int[] xvertsPrime = new int[verts];
        int[] yvertsPrime = new int[verts];

        for (int i = 0; i < verts; i++) {
            xvertsPrime[i] = (int) xVerts[i];
            yvertsPrime[i] = (int) yVerts[i];
        }

        Polygon p = new Polygon(xvertsPrime, yvertsPrime, verts);

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

        int[] ixverts = new int[verts];
        int[] iyverts = new int[verts];

        for (int i = 0; i < verts; i++) {
            ixverts[i] = (int) xVerts[i];
            iyverts[i] = (int) yVerts[i];
        }

        Area area = new Area(new Polygon(ixverts, iyverts, verts));
        Area otherArea = new Area(new Polygon(iotherx, iothery, othernv));

        area.intersect(otherArea);

        return !area.isEmpty();
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

                point.x = xPrime;
                point.y = yPrime;
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

        void detatch() {
            if (poly != null) {
                poly.setFill(poly.getDefaultColor());
                poly.setOrigin(null);
                poly = null;
            }
        }

        void attatch(Poly poly) {
            if (this.poly == null) {
                poly.translate(point.x - poly.getXConnector(), point.y - poly.getYConnector());
                poly.rotate(rotation - poly.getRotation(), poly.getXConnector(), poly.getYConnector());
                poly.setOrigin(this);
                poly.setFill(source.getFill());
                this.poly = poly;
            }
        }

        void translate(double dx, double dy) {
            point.x += dx;
            point.y += dy;

            if (poly != null) {
                poly.translate(dx, dy);
            }
        }

        void rotate(double theta, double x, double y) {
            rotation += theta;

            double cos = Math.cos(theta), sin = Math.sin(theta);

            Point2D.Double p = new Point2D.Double(point.x, point.y);

            point.x -= x;
            point.y -= y;

            p.x = point.x * cos - point.y * sin;
            p.y = point.x * sin + point.y * cos;

            point.x = p.x + x;
            point.y = p.y + y;

            if (poly != null) {
                poly.rotate(theta, x, y);
            }
        }
    }
}