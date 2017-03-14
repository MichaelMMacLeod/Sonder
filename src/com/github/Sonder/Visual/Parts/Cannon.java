package com.github.Sonder.Visual.Parts;

import java.awt.*;
import java.awt.geom.Point2D;

public class Cannon extends Poly {
    public Cannon(double x, double y) {
        super(new Point2D.Double[]
                        {
                                new Point2D.Double(-30, -20),
                                new Point2D.Double(0, -5),
                                new Point2D.Double(30, -5),
                                new Point2D.Double(30, -10),
                                new Point2D.Double(50, -10),
                                new Point2D.Double(50, 10),
                                new Point2D.Double(30, 10),
                                new Point2D.Double(30, 5),
                                new Point2D.Double(0, 5),
                                new Point2D.Double(-30, 20)
                        },
                new Point2D.Double(10, 0),
                new Point2D.Double(x, y),
                Color.GRAY,
                Color.WHITE,
                new Point2D.Double[] {},
                new double[] {},
                new Point2D.Double(-30, 0),
                new Point2D.Double(0, 0));
    }
}
