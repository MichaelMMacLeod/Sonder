package com.github.Sonder.Visual;

import java.awt.*;

public abstract class Part extends Poly {
    private Part[] parts;
    private int nparts = 1;

    public Part(
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
        super(xverts, yverts, verts, cx, cy, x, y, outline, fill, xnodes, ynodes, nodeRotations, nodes, xconnector, yconnector);

        this.parts = new Part[nodes];
    }

    @Override
    public void translate(double dx, double dy) {
        super.translate(dx, dy);

        for (Part part : parts) {
            if (part != null) {
                part.translate(dx, dy);
            }
        }
    }

    public void rotate(double theta) {
        rotate(theta, getCenterX(), getCenterY());
    }

    @Override
    void rotate(double theta, double x, double y) {
        super.rotate(theta, x, y);

        for (Part part : parts) {
            if (part != null) {
                part.rotate(theta, x, y);
            }
        }
    }

    @Override
    public double getCenterX() {
        double x = super.getCenterX();

        for (Part part : parts) {
            if (part != null) {
                x += part.getCenterX();
            }
        }

        return x / nparts;
    }

    @Override
    public double getCenterY() {
        double y = super.getCenterY();

        for (Part part : parts) {
            if (part != null) {
                y += part.getCenterY();
            }
        }

        return y / nparts;
    }
}
