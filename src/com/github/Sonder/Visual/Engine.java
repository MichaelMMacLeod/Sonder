package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;

public class Engine extends Poly {
    public Engine(double x, double y) {
        super(new Point2D.Double[]
                        {
                                new Point2D.Double(-20, -20),
                                new Point2D.Double(30, -30),
                                new Point2D.Double(30, 30),
                                new Point2D.Double(-20, 20)
                        },
                new Point2D.Double(0, 0),
                new Point2D.Double(x, y),
                Color.GRAY,
                Color.WHITE,
                new Point2D.Double[] {},
                new double[] {},
                new Point2D.Double(-20, 0),
                new Point2D.Double(0, 0));
    }
}
