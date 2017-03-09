package com.github.Sonder.Visual;

import java.awt.*;

public class Hull_Long extends Poly {
    public Hull_Long(double x, double y) {
        super(
                new double[] {-40, -30, -30,  100, 100, -30, -30, -40},
                new double[] {-20, -20, -30,  -30,  30,  30,  20,  20},
                8,
                0,
                0,
                x,
                y,
                Color.GRAY,
                Color.WHITE,
                new double[] {           0,            70, 100,          70,           0},
                new double[] {         -30,           -30,   0,          30,          30},
                new double[] {-Math.PI / 2,  -Math.PI / 2,   0, Math.PI / 2, Math.PI / 2},
                5,
                -40,
                0);
    }
}