package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public abstract class Part extends Poly {
    private final Part[] linked;
    private final double[] xlinks;
    private final double[] ylinks;
    private final double[] linkRotations;

    private Part source;

    private Point2D.Double vector;

    double getLinkX(int link) {
        return xlinks[link];
    }
    double getLinkY(int link) {
        return ylinks[link];
    }
    double getLinkRotation(int link) {
        return linkRotations[link];
    }

    Point2D.Double getVector() {
        return vector;
    }

    public Part(
            Point2D.Double vector,
            double[] xverts,
            double[] yverts,
            int nverts,
            double cx,
            double cy,
            double x,
            double y,
            Color outline,
            Color fill,
            double opacity,
            double[] xlinks,
            double[] ylinks,
            double[] linkRotations,
            int nlinks) {
        super(
                xverts,
                yverts,
                nverts,
                cx, cy,
                x,
                y,
                outline,
                fill,
                opacity);

        source = null;

        this.vector = vector;

        linked = new Part[nlinks];
        this.xlinks = Arrays.copyOf(xlinks, nlinks);
        this.ylinks = Arrays.copyOf(ylinks, nlinks);
        this.linkRotations = Arrays.copyOf(linkRotations, nlinks);
    }

    public Part(
            Part source,
            int link,
            double[] xverts,
            double[] yverts,
            int nverts,
            double cx,
            double cy,
            Color outline,
            Color fill,
            double opacity,
            double[] xlinks,
            double[] ylinks,
            double[] linkRotations,
            int nlinks) {
        super(
                xverts,
                yverts,
                nverts,
                cx,
                cy,
                source.getLinkX(link) + source.getX(),
                source.getLinkY(link) + source.getY(),
                outline,
                fill,
                opacity);

        this.source = source;
        this.source.link(this, link);

        vector = source.getVector();

        rotate(source.getLinkRotation(link));

        linked = new Part[nlinks];
        this.xlinks = Arrays.copyOf(xlinks, nlinks);
        this.ylinks = Arrays.copyOf(ylinks, nlinks);
        this.linkRotations = Arrays.copyOf(linkRotations, nlinks);
    }

    private void link(Part part, int link) {
        linked[link] = part;
    }

    public double getCenterX() {
        double x = getX();

        for (Part link : linked) {
            if (link != null) {
                x += link.getCenterX();
            }
        }

        return x / linked.length;
    }

    public double getCenterY() {
        double y = getY();

        for (Part link : linked) {
            if (link != null) {
                y += link.getY();
            }
        }

        return y / linked.length;
    }
}
