package com.github.Sonder.Visual;

import java.awt.*;

public class Hull extends Poly {

    /**
     * Creates a Hull at location (x, y)
     * @param x is the x coordinate of the Capsule.
     * @param y is the y coordinate of the Capsule.
     */
    public Hull(double x, double y) {
        super(
                new double[] {-30,  30, 30, -30},
                new double[] {-20, -30, 30,  20},
                4,
                0,
                0,
                x,
                y,
                Color.GRAY,
                Color.WHITE,
                new double[] {          0, 30,            0},
                new double[] {        -30,  0,           30},
                new double[] {-Math.PI / 2,  0, Math.PI / 2},
                3,
                -30,
                0);
    }
}
