package com.github.Sonder.Visual;

import java.awt.*;

public class Cannon extends Poly {
    public Cannon(double x, double y) {
        super(
                new double[] {-30,  0, 30,  30,  50, 50, 30, 30, 0, -30},
                new double[] {-20, -5, -5, -10, -10, 10, 10,  5, 5,  20},
                10,
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
                -30,
                0);
    }
}
