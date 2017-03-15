package com.github.Sonder.Visual.Parts;

import java.awt.*;
import java.awt.geom.Point2D;

public class Hull extends Poly {
    public Hull(double x, double y) {
        super(new Point2D.Double[]
                        {
                                new Point2D.Double(-40, -20),
                                new Point2D.Double(-30, -20),
                                new Point2D.Double(-30, -30),
                                new Point2D.Double(30, -30),
                                new Point2D.Double(30, 30),
                                new Point2D.Double(-30, 30),
                                new Point2D.Double(-30, 20),
                                new Point2D.Double(-40, 20)
                        },
                new Point2D.Double(0, 0),
                new Point2D.Double(x, y),
                Color.GRAY,
                Color.WHITE,
                new Point2D.Double[]
                        {
                                new Point2D.Double(0, -30),
                                new Point2D.Double(30, 0),
                                new Point2D.Double(0, 30),
                        },
                new double[]
                        {
                                -Math.PI / 2,
                                0,
                                Math.PI / 2
                        },
                new Point2D.Double(-40, 0),
                new Point2D.Double(0, 0));
    }
}