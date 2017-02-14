package com.github.Sonder.Visual;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PartCollection {

    private final Point2D.Double anchor;

    private final Point2D.Double vector;

    private double rotation;
    private final ArrayList<Part> parts;

    public PartCollection() {
        vector = new Point2D.Double();
        anchor = new Point2D.Double();
        rotation = 0;
        parts = new ArrayList<>();
    }

    public double getAnchorX() {
        return anchor.x;
    }

    public double getAnchorY() {
        return anchor.y;
    }

    public double getRotation() {
        return rotation;
    }

    public void translateVector(double dx, double dy) {
        vector.x += dx;
        vector.y += dy;
    }

    public void thrust() {
        for (Part part : parts) {
            if (part instanceof Engine) {
                part.update();
            }
        }
    }

    public void update() {
        translate(vector.x, vector.y);
    }

    private void translate(double dx, double dy) {
        for (Part part : parts) {
            part.translate(dx, dy);
        }
        updateAnchor();
    }

    public void addPart(Part part) {
        parts.add(part);
        updateAnchor();
    }

    private void updateAnchor() {
        anchor.setLocation(0, 0);

        for (Part p : parts) {
            anchor.x += p.getX();
            anchor.y += p.getY();
        }

        anchor.x /= parts.size();
        anchor.y /= parts.size();
    }

    public void rotate(double theta) {
        rotation += theta;

        for (Part part : parts) {
            part.setAnchor(anchor.x, anchor.y);
            part.rotate(theta);
        }
    }

    public Poly[] getShapes() {
        return parts.toArray(new Part[0]);
    }

    public Point2D.Double getLocation() {
        return new Point2D.Double(anchor.x, anchor.y);
    }
}