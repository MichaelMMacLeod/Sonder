package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Capsule extends Part {

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
                Color.GRAY,
                Color.WHITE,
                new double[] {    -10,           0, 10,            0},
                new double[] {      0,         -10,  0,           10},
                new double[] {Math.PI, Math.PI / 2,  0, -Math.PI / 2},
                4);
    }
}
