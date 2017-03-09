package com.github.Sonder.Visual;

import java.awt.*;

public class Engine extends Poly {
    public Engine(double x, double y) {
        super(
                new double[] {-20, 30, 30, -20},
                new double[] {-20, -30, 30, 20},
                4,
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
