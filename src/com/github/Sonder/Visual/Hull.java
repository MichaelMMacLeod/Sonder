package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;

public class Hull extends Part {
    public Hull(
            Point2D.Double vector,
            double x,
            double y) {
        super(
                vector,
                new double[] {-10,  10, 10, -10},
                new double[] {-10, -10, 10,  10},
                4,
                0,
                0,
                x,
                y,
                Color.BLACK,
                Color.WHITE,
                100,
                new double[] {    -20,           0, 20,            0},
                new double[] {      0,         -20,  0,           20},
                new double[] {Math.PI, Math.PI / 2,  0, -Math.PI / 2},
                4);
    }

    public Hull(
            Part source,
            int link) {
        super(
                source,
                link,
                new double[] {-10,  10, 10, -10},
                new double[] {-10, -10, 10,  10},
                4,
                0,
                0,
                Color.BLACK,
                Color.WHITE,
                100,
                new double[] {    -20,           0, 20,            0},
                new double[] {      0,         -20,  0,           20},
                new double[] {Math.PI, Math.PI / 2,  0, -Math.PI / 2},
                4);
    }
}