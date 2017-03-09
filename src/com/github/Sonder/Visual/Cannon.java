package com.github.Sonder.Visual;

import java.awt.*;

public class Cannon extends Poly {
    public Cannon(double x, double y) {
        super(
                new double[] {-30, 30, 30, 35, 35, 30, 30, -30},
                new double[] { -5, -5,-10,-10, 10, 10, 5, 5},
                8,
                0,
                0,
                x,
                y,
                Color.GRAY,
                Color.WHITE,
                new double[] {},
                new double[] {},
                new double[] {},
                0,
                -20,
                0);
    }
}
