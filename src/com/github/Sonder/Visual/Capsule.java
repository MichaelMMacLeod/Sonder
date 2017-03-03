package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;

public class Capsule extends Poly {
    public Capsule(Point2D.Double location) {
        super(
                new Point2D.Double[]
                        {
                                new Point2D.Double(-30, -30),
                                new Point2D.Double(30, -30),
                                new Point2D.Double(30, 30),
                                new Point2D.Double(-30, 30)
                        },
                new Point2D.Double(0, 0),
                location,
                Color.GRAY,
                Color.BLUE,
                new Point2D.Double[]
                        {
                                new Point2D.Double(-30, 0),
                                new Point2D.Double(0, -30),
                                new Point2D.Double(30, 0),
                                new Point2D.Double(0, 30)
                        },
                new double[] {Math.PI, -Math.PI / 2,  0, Math.PI / 2},
                new Point2D.Double(0, 0));
    }
}
