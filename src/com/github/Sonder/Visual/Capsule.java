package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Capsule extends Poly {

    /**
     * Creates a Capsule at location (x, y)
     * @param x is the x coordinate of the Capsule.
     * @param y is the y coordinate of the Capsule.
     */
    public Capsule(double x, double y) {
        super(
                new double[] {-30,  30, 30, -30},
                new double[] {-30, -30, 30,  30},
                4,
                0,
                0,
                x,
                y,
                Color.GRAY,
                Color.BLUE,
                new double[] {    -30,           0, 30,            0},
                new double[] {      0,         -30,  0,           30},
                new double[] {Math.PI, -Math.PI / 2,  0, Math.PI / 2},
                4,
                0,
                0);
    }
}
