package com.github.Sonder.Visual;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PartCollection {
    // TODO: make location hold centroid of parts, update each time addPart is called or shapes move
    private Point2D.Double location;
    private double rotation;
    private ArrayList<Part> parts;

    public PartCollection() {
        location = new Point2D.Double(0, 0);
        rotation = 0;
        parts = new ArrayList<>();
    }

    public void addPart(Part part) {
        parts.add(part);
    }

    public Drawn[] getShapes() {
        return parts.toArray(new Part[0]);
    }

    public Point2D.Double getLocation() {
        return location;
    }
}