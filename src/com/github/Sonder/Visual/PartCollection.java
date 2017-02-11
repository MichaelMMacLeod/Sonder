package com.github.Sonder.Visual;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PartCollection {

    /**
     * Contains the centroid of all parts.
     */
    private final Point2D.Double location;

    private double rotation;
    private ArrayList<Part> parts;

    public PartCollection() {
        location = new Point2D.Double(0, 0);
        rotation = 0;
        parts = new ArrayList<>();
    }

    public void addPart(Part part) {
        parts.add(part);

        location.setLocation(0, 0);
        for (Part p : parts) {
            Point2D.Double point = p.getPoint();
            location.x += point.x;
            location.y += point.y;
        }
        location.x /= parts.size();
        location.y /= parts.size();
    }

    public void rotate(double theta) {
        for (Part part : parts) {
            part.setAnchor(location.x, location.y);
            part.rotate(theta);
        }
    }

    public Drawn[] getShapes() {
        return parts.toArray(new Part[0]);
    }

    public Point2D.Double getLocation() {
        return location;
    }
}