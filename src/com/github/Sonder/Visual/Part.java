package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

import java.util.HashMap;

public abstract class Part {
    private HashMap<String, Double> attributes;

    private final Drawn shape;

    /**
     * Holds the x and y coordinates of the shape relative to the rest of the ship.
     */
    private final Point2D.Double location;

    public Part(Drawn shape, HashMap<String, Double> attributes, Point2D.Double location) {
        this.shape = shape;
        this.attributes = attributes;
        this.location = location;
    }

    public abstract void update();

    public final Drawn getShape() {
        return shape;
    }

    public final double getAttribute(String key) {
        return attributes.get(key);
    }
}