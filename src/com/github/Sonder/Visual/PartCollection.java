package com.github.Sonder.Visual;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PartCollection {

    private final Point2D.Double anchor;

    private double rotation;
    private final ArrayList<Part> parts;

    public PartCollection() {
        anchor = new Point2D.Double();
        rotation = 0;
        parts = new ArrayList<>();
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

        System.out.println(anchor);
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