package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Poly {
    private final Point2D.Double center;

    private final double[] xverts, yverts;
    private final int nverts;

    private double rotation;
    private final Point2D.Double anchor;

    private Color color;
    private boolean isFilled;

    public double getX() {
        return center.x;
    }
    public double getY() {
        return center.y;
    }

    double[] getXVertices() {
        return Arrays.copyOf(xverts, nverts);
    }
    double[] getYVertices() {
        return Arrays.copyOf(yverts, nverts);
    }

    int getNumberOfVertices() {
        return nverts;
    }

    public double getRotation() {
        return rotation;
    }

    Color getColor() {
        return color;
    }
    boolean isFilled() {
        return isFilled;
    }

    private void setAnchor(double x, double y) {
        anchor.x = x;
        anchor.y = y;
    }

    Poly(double[] xverts,
         double[] yverts,
         double x,
         double y,
         double anchorx,
         double anchory,
         Color color,
         boolean isFilled) {
        if (xverts.length != yverts.length) {
            System.out.println("Error: unequal number of vertices");
        }

        nverts = xverts.length < yverts.length ? xverts.length : yverts.length;

        center = new Point2D.Double();

        for (int i = 0; i < nverts; i++) {
            center.x += xverts[i];
            center.y += yverts[i];
        }
        center.x /= nverts;
        center.y /= nverts;

        double xdiff = x - center.x;
        double ydiff = y - center.y;

        for (int i = 0; i < nverts; i++) {
            xverts[i] += xdiff;
            yverts[i] += ydiff;
        }

        center.x += xdiff;
        center.y += ydiff;

        this.xverts = Arrays.copyOf(xverts, nverts);
        this.yverts = Arrays.copyOf(yverts, nverts);

        this.rotation = 0;

        anchor = new Point2D.Double(anchorx, anchory);
        this.color = color;
        this.isFilled = isFilled;
    }

    void translate(double dx, double dy) {
        for (int i = 0; i < nverts; i++) {
            xverts[i] += dx;
            yverts[i] += dy;
        }

        updateCenter();
    }

    private void updateCenter() {
        center.x = 0;
        center.y = 0;

        for (int i = 0; i < nverts; i++) {
            center.x += xverts[i];
            center.y += yverts[i];
        }

        center.x /= nverts;
        center.y /= nverts;
    }

    void rotate(double theta, double x, double y) {
        setAnchor(x, y);

        rotation += theta;

        double cos = Math.cos(theta), sin = Math.sin(theta);

        for (int i = 0; i < nverts; i++) {
            xverts[i] -= anchor.x;
            yverts[i] -= anchor.y;
        }

        double[] xvertsNew = new double[nverts];
        double[] yvertsNew = new double[nverts];

        for (int i = 0; i < nverts; i++) {
            xvertsNew[i] = xverts[i] * cos - yverts[i] * sin;
            yvertsNew[i] = xverts[i] * sin + yverts[i] * cos;
        }

        for (int i = 0; i < nverts; i++) {
            xverts[i] = xvertsNew[i] + anchor.x;
            yverts[i] = yvertsNew[i] + anchor.y;
        }

        updateCenter();
    }

    public void scale(double scalar) {
        for (int i = 0; i < nverts; i++) {
            xverts[i] *= scalar;
            yverts[i] *= scalar;
        }
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

        int[] ixverts = new int[nverts];
        int[] iyverts = new int[nverts];

        for (int i = 0; i < nverts; i++) {
            ixverts[i] = (int) xverts[i];
            iyverts[i] = (int) yverts[i];
        }

        Area area = new Area(new Polygon(ixverts, iyverts, nverts));
        Area otherArea = new Area(new Polygon(iotherx, iothery, othernv));

        area.intersect(otherArea);

        return !area.isEmpty();
    }
}
