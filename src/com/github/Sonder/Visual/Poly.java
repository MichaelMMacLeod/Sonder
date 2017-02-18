package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Arrays;

public class Poly {
    private double cx;
    private double cy;

    private final double[] xverts, yverts;
    private final int nverts;

    private double rotation;

    private Color outline;
    private Color fill;
    private double opacity;

    void setOutline(Color color) {
        outline = color;
    }
    void setFill(Color color) {
        fill = color;
    }

    public double getX() {
        return cx;
    }
    public double getY() {
        return cy;
    }

    public double[] getXVertices() {
        return Arrays.copyOf(xverts, nverts);
    }
    public double[] getYVertices() {
        return Arrays.copyOf(yverts, nverts);
    }

    public int getNumberOfVertices() {
        return nverts;
    }

    public double getRotation() {
        return rotation;
    }

    public Color getOutline() {
        return outline;
    }
    public Color getFill() {
        return fill;
    }
    public double getOpacity() {
        return opacity;
    }

    Poly(
            double[] xverts,
            double[] yverts,
            int nverts,
            double cx,
            double cy,
            double x,
            double y,
            Color outline,
            Color fill,
            double opacity) {
        this.xverts = Arrays.copyOf(xverts, nverts);
        this.yverts = Arrays.copyOf(yverts, nverts);
        this.nverts = nverts;
        this.cx = cx;
        this.cy = cy;
        this.outline = outline;
        this.fill = fill;
        this.opacity = opacity;

        setLocation(x, y);
    }

    private void setLocation(double x, double y) {
        double xdiff = x - cx;
        double ydiff = y - cy;

        cx += xdiff;
        cy += ydiff;

        for (int i = 0; i < nverts; i++) {
            xverts[i] += xdiff;
            yverts[i] += ydiff;
        }
    }

    public void translate(double dx, double dy) {
        cx += dx;
        cy += dy;

        for (int i = 0; i < nverts; i++) {
            xverts[i] += dx;
            yverts[i] += dy;
        }
    }

    public void rotate(double theta, double x, double y) {
        rotation += theta;

        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        for (int i = 0; i < nverts; i++) {
            xverts[i] -= x;
            yverts[i] -= y;
        }
        cx -= x;
        cy -= y;

        double[] xprimes = new double[nverts];
        double[] yprimes = new double[nverts];
        double cxprime;
        double cyprime;

        for (int i = 0; i < nverts; i++) {
            xprimes[i] = xverts[i] * cos - yverts[i] * sin;
            yprimes[i] = xverts[i] * sin + yverts[i] * cos;
        }
        cxprime = cx * cos - cy * sin;
        cyprime = cx * sin + cy * cos;

        for (int i = 0; i < nverts; i++) {
            xverts[i] = xprimes[i] + x;
            yverts[i] = yprimes[i] + y;
        }
        cx = cxprime + x;
        cy = cyprime + y;
    }

    public void rotate(double theta) {
        rotation += theta;

        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        for (int i = 0; i < nverts; i++) {
            xverts[i] -= cx;
            yverts[i] -= cy;
        }

        double[] xprimes = new double[nverts];
        double[] yprimes = new double[nverts];

        for (int i = 0; i < nverts; i++) {
            xprimes[i] = xverts[i] * cos - yverts[i] * sin;
            yprimes[i] = xverts[i] * sin + yverts[i] * cos;
        }

        for (int i = 0; i < nverts; i++) {
            xverts[i] = xprimes[i] + cx;
            yverts[i] = yprimes[i] + cy;
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