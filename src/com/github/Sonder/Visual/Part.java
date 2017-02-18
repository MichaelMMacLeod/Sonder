package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Part extends Poly {
    private final Part[] linked;
    private final double[] xlinks;
    private final double[] ylinks;
    private final double[] linkRotations;

    private Part source;

    private Point2D.Double vector;

    private double getLinkX(int link) {
        return xlinks[link];
    }
    private double getLinkY(int link) {
        return ylinks[link];
    }
    private double getLinkRotation(int link) {
        return linkRotations[link];
    }

    private Point2D.Double getVector() {
        return vector;
    }

    Part(
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

    Part(
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

        vector = source.getVector();

        rotate(source.getLinkRotation(link));

        linked = new Part[nlinks];
        this.xlinks = Arrays.copyOf(xlinks, nlinks);
        this.ylinks = Arrays.copyOf(ylinks, nlinks);
        this.linkRotations = Arrays.copyOf(linkRotations, nlinks);

        this.source.link(this, link);
    }

    private void link(Part part, int link) {
        linked[link] = part;
        part.setFill(getFill());
        part.setOutline(getOutline());
    }

    public double getCenterX() {
        double x = getX();

        int count = 1;
        for (Part link : linked) {
            if (link != null) {
                x += link.getCenterX();
                count++;
            }
        }

        return x / count;
    }

    public double getCenterY() {
        double y = getY();

        int count = 1;
        for (Part link : linked) {
            if (link != null) {
                y += link.getCenterY();
                count++;
            }
        }

        return y / count;
    }

    public ArrayList<Part> getParts() {
        ArrayList<Part> allParts = new ArrayList<>();
        allParts.add(this);

        for (Part link : linked) {
            if (link != null) {
                allParts.addAll(link.getParts());
            }
        }

        return allParts;
    }

    @Override
    public void setOutline(Color color) {
        super.setOutline(color);

        for (Part link : linked) {
            if (link != null) {
                link.setOutline(color);
            }
        }
    }

    @Override
    public void setFill(Color color) {
        super.setFill(color);

        for (Part link : linked) {
            if (link != null) {
                link.setFill(color);
            }
        }
    }
}
