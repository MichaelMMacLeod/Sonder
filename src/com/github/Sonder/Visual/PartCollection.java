package com.github.Sonder.Visual;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PartCollection {
    private Point2D.Double location;
    private double rotation;
    private ArrayList<Part> parts;

    public void addPart(Part part) {
        parts.add(part);
    }

    public Drawn[] getParts() {
        return parts.toArray(new Part[0]);
    }
}