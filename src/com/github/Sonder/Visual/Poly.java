package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Poly {



    private Node[] _nodes;





    /**
     * Relationships with other Polys
     */

    private Poly parent;

    private Poly[] children;
    private int nchildren;

    // The number of non-null elements of children plus one for this Poly.
    private int npolys;

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

    /**
     * Nodes represent places which other Polys can connect to.
     */

    private double[] xnodes;
    private double[] ynodes;
    private double[] nodeRotations;
    private int nodes;

    public double[] getXNodes() {
        return Arrays.copyOf(xnodes, nodes);
    }

    public double[] getYNodes() {
        return Arrays.copyOf(ynodes, nodes);
    }

    public double[] getNodeRotations() {
        return Arrays.copyOf(nodeRotations, nodes);
    }

    public int getNumberOfNodes() {
        return nodes;
    }

    /**
     * The points defining the shape of the Poly
     */
    private double[] xverts;
    private double[] yverts;
    private int verts;

    public double[] getXVertices() {
        return Arrays.copyOf(xverts, verts);
    }

    public double[] getYVertices() {
        return Arrays.copyOf(yverts, verts);
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

    public double getRotation() {
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

    /**
     * Creates a Polygon.
     *
     * @param xverts        are the x points defining the shape of the Poly.
     * @param yverts        are the y points defining the shape of the Poly.
     * @param verts         is the number of vertices.
     * @param cx            is the x coordinate of the center of the Poly.
     * @param cy            is the y coordinate of the center of the Poly.
     * @param x             is the x coordinate of the location of the Poly.
     * @param y             is the y coordinate of the location of the Poly.
     * @param outline       is the color of the outline of the Poly.
     * @param fill          is the color of the inside of the Poly.
     * @param xnodes        are the x points which other shapes can connect to.
     * @param ynodes        are the y points which other shapes can connect to.
     * @param nodeRotations are the rotations that connected parts have.
     * @param nodes         is the number of nodes.
     * @param xconnector    is the x coordinate of the point where this Poly connects to other Polys.
     * @param yconnector    is the y coordinate of the point where this Poly connects to other Polys.
     */
    public Poly(
            double[] xverts,
            double[] yverts,
            int verts,
            double cx,
            double cy,
            double x,
            double y,
            Color outline,
            Color fill,
            double[] xnodes,
            double[] ynodes,
            double[] nodeRotations,
            int nodes,
            double xconnector,
            double yconnector) {
        this.xverts = Arrays.copyOf(xverts, verts);
        this.yverts = Arrays.copyOf(yverts, verts);
        this.verts = verts;
        this.cx = cx;
        this.cy = cy;
        this.outline = outline;
        this.fill = fill;
        this.xnodes = Arrays.copyOf(xnodes, nodes);
        this.ynodes = Arrays.copyOf(ynodes, nodes);
        this.nodeRotations = Arrays.copyOf(nodeRotations, nodes);
        this.nodes = nodes;
        this.xconnector = xconnector;
        this.yconnector = yconnector;
        parent = null;
        children = new Poly[nodes];
        nchildren = nodes;
        npolys = 1;

        _nodes = new Node[nodes];
        for (int i = 0; i < _nodes.length; i++) {
            _nodes[i] = new Node(this, new Point2D.Double(xnodes[i], ynodes[i]), nodeRotations[i]);
        }

        moveTo(x, y);
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

    public void moveTo(double x, double y) {
        double dx = x - cx;
        double dy = y - cy;

        cx += dx;
        cy += dy;

        xconnector += dx;
        yconnector += dy;

        for (int i = 0; i < verts; i++) {
            xverts[i] += dx;
            yverts[i] += dy;
        }

        for (int i = 0; i < nodes; i++) {
            xnodes[i] += dx;
            ynodes[i] += dy;
        }

        for (Node n : _nodes) {
            n.point.x += dx;
            n.point.y += dy;
            n.poly.moveTo(x, y);
        }
    }

    public void translate(double dx, double dy) {
        cx += dx;
        cy += dy;

        xconnector += dx;
        yconnector += dy;

        for (int i = 0; i < verts; i++) {
            xverts[i] += dx;
            yverts[i] += dy;
        }

        for (int i = 0; i < nodes; i++) {
            xnodes[i] += dx;
            ynodes[i] += dy;
        }

        for (Poly child : children) {
            if (child != null) {
                child.translate(dx, dy);
            }
        }

        for (Node n : _nodes) {
            n.point.x += dx;
            n.point.y += dy;
            n.poly.translate(dx, dy);
        }
    }

    public void rotate(double theta, double x, double y) {
        rotation += theta;

        for (int i = 0; i < nodes; i++) {
            nodeRotations[i] += theta;
        }

        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        cx -= x;
        cy -= y;

        xconnector -= x;
        yconnector -= y;

        for (int i = 0; i < verts; i++) {
            xverts[i] -= x;
            yverts[i] -= y;
        }

        for (int i = 0; i < nodes; i++) {
            xnodes[i] -= x;
            ynodes[i] -= y;
        }

        double cxPrime;
        double cyPrime;

        double xconnectorPrime;
        double yconnectorPrime;

        double[] xvertsPrime = new double[verts];
        double[] yvertsPrime = new double[verts];

        double[] xnodesPrime = new double[nodes];
        double[] ynodesPrime = new double[nodes];

        cxPrime = cx * cos - cy * sin;
        cyPrime = cx * sin + cy * cos;

        xconnectorPrime = xconnector * cos - yconnector * sin;
        yconnectorPrime = xconnector * sin + yconnector * cos;

        for (int i = 0; i < verts; i++) {
            xvertsPrime[i] = xverts[i] * cos - yverts[i] * sin;
            yvertsPrime[i] = xverts[i] * sin + yverts[i] * cos;
        }

        for (int i = 0; i < nodes; i++) {
            xnodesPrime[i] = xnodes[i] * cos - ynodes[i] * sin;
            ynodesPrime[i] = xnodes[i] * sin + ynodes[i] * cos;
        }

        cx = cxPrime + x;
        cy = cyPrime + y;

        xconnector = xconnectorPrime + x;
        yconnector = yconnectorPrime + y;

        for (int i = 0; i < verts; i++) {
            xverts[i] = xvertsPrime[i] + x;
            yverts[i] = yvertsPrime[i] + y;
        }

        for (int i = 0; i < nodes; i++) {
            xnodes[i] = xnodesPrime[i] + x;
            ynodes[i] = ynodesPrime[i] + y;
        }

        for (Poly child : children) {
            if (child != null) {
                child.rotate(theta, x, y);
            }
        }

        for (Node n : _nodes) {
            Point2D.Double p = new Point2D.Double(n.point.x, n.point.y);
            n.point.x -= x;
            n.point.y -= y;
            p.x = n.point.x * cos - n.point.y * sin;
            p.y = n.point.x * sin + n.point.y * cos;
            n.point.x = p.x + x;
            n.point.y = p.y + y;
            n.poly.rotate(theta, x, y);
        }
    }

    public boolean contains(double x, double y) {
        int[] xvertsPrime = new int[verts];
        int[] yvertsPrime = new int[verts];

        for (int i = 0; i < verts; i++) {
            xvertsPrime[i] = (int) xverts[i];
            yvertsPrime[i] = (int) yverts[i];
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
            ixverts[i] = (int) xverts[i];
            iyverts[i] = (int) yverts[i];
        }

        Area area = new Area(new Polygon(ixverts, iyverts, verts));
        Area otherArea = new Area(new Polygon(iotherx, iothery, othernv));

        area.intersect(otherArea);

        return !area.isEmpty();
    }
}