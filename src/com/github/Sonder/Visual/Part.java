package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

import java.util.HashMap;

public abstract class Part {
    private final Ship2 parent;

    private final HashMap<String, Double> attributes;

    private final Drawn shape;

    public final HashMap<String, Ship2.Command> actions;

    /**
     * Holds the x and y coordinates of the shape relative to the rest of the ship.
     */
    private final Point2D.Double location;

    public Part(Ship2 parent, Drawn shape, HashMap<String, Double> attributes, Point2D.Double location, HashMap<String, Ship2.Command> actions) {
        this.parent = parent;
        this.shape = shape;
        this.attributes = attributes;
        this.location = location;
        this.actions = actions;
    }

    public abstract void update();

    public Ship2.Command action(String key) {
        return actions.get(key);
    }

    public final String[] getKeys() {
        return (String[]) actions.keySet().toArray();
    }

    public final Drawn getShape() {
        return shape;
    }

    public final double getAttribute(String key) {
        return attributes.get(key);
    }
}