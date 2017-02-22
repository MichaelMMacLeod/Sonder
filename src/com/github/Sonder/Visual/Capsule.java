package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Capsule extends Poly {

    private ArrayList<Poly> modules;

    /**
     * Creates a Capsule at location (x, y)
     * @param x is the x coordinate of the Capsule.
     * @param y is the y coordinate of the Capsule.
     */
    public Capsule(double x, double y) {
        super(
                new double[] {-10,  10, 10, -10},
                new double[] {-10, -10, 10,  10},
                4,
                0,
                0,
                x,
                y,
                Color.BLACK,
                Color.WHITE,
                new double[] {-10,   0, 10,  0},
                new double[] {  0, -10,  0, 10},
                4);
    }

    public Point2D.Double getCenterOfShip() {
        Point2D.Double center = new Point2D.Double(getCenterX(), getCenterY());

        if (modules.size() > 0) {
            for (Poly module : modules) {
                center.x += module.getCenterX();
                center.y += module.getCenterY();
            }

            center.x /= modules.size();
        }

        return center;
    }
}
