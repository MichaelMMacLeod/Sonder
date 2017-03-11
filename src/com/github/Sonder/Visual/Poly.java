package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

public class Poly {
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

    ////////////////////////////////
    // Polygon shape and position //
    ////////////////////////////////

    // Contains the (x, y) points defining the vertices of the polygon.
    private final Point2D.Double[] vertices;

    // Returns every x value in vertices.
    double[] getXVertices() {
        double[] xVertices = new double[vertices.length];
        for (int i = 0; i < xVertices.length; i++) {
            xVertices[i] = vertices[i].x;
        }
        return xVertices;
    }

    // Returns every y value in vertices.
    double[] getYVertices() {
        double[] yVertices = new double[vertices.length];
        for (int i = 0; i < yVertices.length; i++) {
            yVertices[i] = vertices[i].y;
        }
        return yVertices;
    }

    // Returns the number of vertices.
    int getNumberOfVertices() {
        return vertices.length;
    }

    // The (x, y) location of the center of this polygon.
    private final Point2D.Double center;

    // Returns the x value of the center.
    public double getCenterX() {
        return center.x;
    }

    // Returns the y value of the center.
    public double getCenterY() {
        return center.y;
    }

    // This polygon's rotation in radians.
    private double rotation;

    // Returns this polygon's rotation in radians.
    private double getRotation() {
        return rotation;
    }

    ///////////////////
    // Polygon chain //
    ///////////////////

    /**
     * A Node represents a connection between a parent polygon and a child polygon.
     *
     * When a polygon is dragged close to another polygon, the dragged polygon will "snap" to one of the other polygon's
     * Nodes. The snapping polygon will be rotated to the rotation of the Node, and also translated to
     * the location of the Node.
     *
     * When the parent polygon is translated/rotated, a message is sent to all of its child Nodes. Each Node will then
     * relay the message to the child polygon. The child will then relay the message to all of its children, and so on.
     *
     * Each Node contains a reference to the parent (non-null) polygon (source), and a reference to the (possibly null)
     * child polygon (poly).
     *
     * Each polygon contains exactly one (possibly null) reference to its parent Node, and zero or more (non-null)
     * references to its child nodes. The parent reference is null when the polygon is not attached to another polygon.
     *
     * Nodes will never detach from their source polygon. They can, however, detach their child polygon.
     */
    private class Node {
        // The polygon that this Node is attached to.
        private final Poly source;

        // The polygon which is attached to this Node.
        private Poly poly;

        // The (x, y) location of this Node
        private final Point2D.Double point;

        // The rotation in radians that a polygon attached to this Node will have.
        private double rotation;

        /**
         * Creates a Node
         *
         * @param source   is the polygon that this Node is attached to.
         * @param point    is the (x, y) location of this Node.
         * @param rotation is the rotation in radians that a polygon attached to this Node will have.
         */
        Node(Poly source, Point2D.Double point, double rotation) {
            this.source = source;
            this.point = point;
            this.rotation = rotation;
        }

        // Detaches the polygon currently attached to this Node.
        void detach() {
            if (poly != null) {
                poly.setOrigin(null);
                poly.setVector(new Point2D.Double(poly.getVector().x, poly.getVector().y));
                poly = null;
            }
        }

        // Attaches a polygon to this Node.
        void attach(Poly poly) {
            if (this.poly == null) {
                poly.chainTranslate(point.x - poly.getXConnector(), point.y - poly.getYConnector());
                poly.chainRotate(rotation - poly.getRotation(), poly.getXConnector(), poly.getYConnector());
                poly.setOrigin(this);
                poly.setVector(source.getVector());
                this.poly = poly;
            }
        }
    }

    // The references to the Nodes attached to this polygon.
    private final Node[] nodes;

    // Returns the x position values of each Node attached to this polygon.
    public double[] getXNodes() {
        double[] xNodes = new double[nodes.length];
        for (int i = 0; i < xNodes.length; i++) {
            xNodes[i] = nodes[i].point.x;
        }
        return xNodes;
    }

    // Returns the y position values of each Node attached to this polygon.
    public double[] getYNodes() {
        double[] yNodes = new double[nodes.length];
        for (int i = 0; i < yNodes.length; i++) {
            yNodes[i] = nodes[i].point.y;
        }
        return yNodes;
    }

    // Returns the number of Nodes attached to this polygon.
    public int getNumberOfNodes() {
        return nodes.length;
    }

    // Checks if this polygon contains a certain other polygon in its chain.
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

    // Attaches a polygon to one of this polygon's Nodes.
    public void attach(Poly poly, int node) {
        nodes[node].attach(poly);
    }

    // The reference to the Node that this polygon is attached to.
    private Node origin;

    // Changes what Node this polygon is attached to.
    private void setOrigin(Node origin) {
        this.origin = origin;
    }

    // The (x, y) point where this polygon attaches to other Nodes.
    private final Point2D.Double connector;

    // Returns the x value of the connector.
    public double getXConnector() {
        return connector.x;
    }

    // Returns the y value of the connector.
    public double getYConnector() {
        return connector.y;
    }

    // Detaches this polygon from its parent Node.
    public void detach() {
        if (origin != null) {
            origin.detach();
        }
    }

    //////////////////////
    // Polygon movement //
    //////////////////////

    // Moves this polygon to a certain (x, y) point. After calling moveTo(a, b), center.x will be a, and center.y will
    // be b.
    private void moveTo(double x, double y) {
        double dx = x - center.x;
        double dy = y - center.y;

        Transform.translate(dx, dy, center);
        Transform.translate(dx, dy, connector);
        Transform.translate(dx, dy, vertices);

        for (Node n : nodes) {
            Transform.translate(dx, dy, n.point);
        }
    }

    // Executes moveTo down the polygon chain.
    private void chainMoveTo(double x, double y) {
        moveTo(x, y);
        for (Node n : nodes) {
            if (n.poly != null) {
                n.poly.chainMoveTo(x, y);
            }
        }
    }

    // Translates this polygon by dx in the x dimension, and dy in the y dimension.
    public void translate(double dx, double dy) {
        Transform.translate(dx, dy, center);
        Transform.translate(dx, dy, connector);
        Transform.translate(dx, dy, vertices);

        for (Node n : nodes) {
            Transform.translate(dx, dy, n.point);
        }
    }

    // Executes translate down the polygon chain.
    public void chainTranslate(double x, double y) {
        translate(x, y);
        for (Node n : nodes) {
            if (n.poly != null) {
                n.poly.chainTranslate(x, y);
            }
        }
    }

    // Rotates this polygon around a certain (x, y) point by the radian value theta.
    private void rotate(double theta, double x, double y) {
        rotation += theta;

        Transform.rotate(theta, x, y, center);
        Transform.rotate(theta, x, y, connector);
        Transform.rotate(theta, x, y, vertices);

        for (Node n : nodes) {
            n.rotation += theta;
            Transform.rotate(theta, x, y, n.point);
        }
    }

    // Executes rotate down the polygon chain.
    private void chainRotate(double theta, double x, double y) {
        rotate(theta, x, y);
        for (Node n : nodes) {
            if (n.poly != null) {
                n.poly.chainRotate(theta, x, y);
            }
        }
    }

    // The vector representing the speed and direction of this polygon's movement.
    private Point2D.Double vector;

    // Increases this polygon's vector in the direction it is facing by a certain value.
    public void thrust(double force) {
        vector.x += force * Math.cos(rotation);
        vector.y += force * Math.sin(rotation);
    }

    // Rotates this polygon by a certain radian value.
    public void rotate(double theta) {
        rotate(theta, getCenterX(), getCenterY());
    }

    public void chainRotate(double theta) {
        rotate(theta);
        for (Node n : nodes) {
            if (n.poly != null) {
                n.poly.chainRotate(theta, getCenterX(), getCenterY());
            }
        }
    }

    // Sets this polygon's vector reference to a different vector reference.
    //
    // Polygons in the same chain all have their vector reference pointing to the same vector.
    private void setVector(Point2D.Double vector) {
        this.vector = vector;

        for (Node n : nodes) {
            if (n.poly != null) {
                n.poly.setVector(vector);
            }
        }
    }

    // Returns this polygon's vector.
    private Point2D.Double getVector() {
        return vector;
    }

    /////////////////////
    // Polygon updates //
    /////////////////////

    // Updates this polygon.
    public void update() {
        translate(vector.x, vector.y);
    }

    ///////////////
    // Utilities //
    ///////////////

    /**
     * Contains methods which modify (x, y) values in Point2D.Double objects.
     */
    private static class Transform {
        // Translates point(s) by dx in the x dimension, and dy in the y dimension.
        static void translate(double dx, double dy, Point2D.Double... points) {
            for (Point2D.Double point : points) {
                point.x += dx;
                point.y += dy;
            }
        }

        // Rotates point(s) around a certain (x, y) point by the radian value theta.
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

    /////////////////////
    // Polygon visuals //
    /////////////////////

    // The outline Color of this polygon.
    private Color outline;

    // Returns the Color of this polygon's outline.
    Color getOutline() {
        return outline;
    }

    // The fill Color of this polygon.
    private Color fill;

    // Returns the Color of this polygon's fill.
    Color getFill() {
        return fill;
    }

    ////////////////////////////////////////
    // Polygon collision and intersection //
    ////////////////////////////////////////

    // Checks if this polygon contains a certain (x, y) point.
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

    // Checks if this polygon overlaps a certain other polygon.
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
}