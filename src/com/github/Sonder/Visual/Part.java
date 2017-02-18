package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Part extends Poly {
    private Part[] linked;
    private double[] xlinks;
    private double[] ylinks;
    private double[] linkRotations;

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
            double vectorx,
            double vectory,
            double[] xverts,
            double[] yverts,
            double x,
            double y,
            Color color,
            boolean isFilled,
            double[] xlinks,
            double[] ylinks,
            double[] linkRotations,
            int links) {
        super(xverts, yverts, x, y, 0, 0, color, isFilled);

        source = null;

        vector = new Point2D.Double(vectorx, vectory);

        linked = new Part[links];
        this.xlinks = Arrays.copyOf(xlinks, links);
        this.ylinks = Arrays.copyOf(ylinks, links);
        this.linkRotations = Arrays.copyOf(linkRotations, links);
    }

    public Part(
            Part source,
            int link,
            double[] xverts,
            double[] yverts,
            Color color,
            boolean isFilled,
            double[] xlinks,
            double[] ylinks,
            double[] linkRotations,
            int links) {
        super(xverts, yverts, source.getLinkX(link), source.getLinkY(link), 0, 0, color, isFilled);

        this.source = source;
        this.source.link(this, link);

        vector = source.getVector();

        rotate(source.getLinkRotation(link), getX(), getY());

        linked = new Part[links];
        this.xlinks = Arrays.copyOf(xlinks, links);
        this.ylinks = Arrays.copyOf(ylinks, links);
        this.linkRotations = Arrays.copyOf(linkRotations, links);
    }

    private void link(Part part, int link) {
        linked[link] = part;
    }
}
