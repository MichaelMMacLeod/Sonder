package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Part extends Chain implements Outline {
    /**
     * Creates a Part object defined by a series of points which can be translated and rotated.
     * @param points is the list of points to be copied into this object.
     */
    Part(double ax, double ay, double r, Point2D.Double[] childPoints, double[] childRotations, Point2D.Double link,
         Point2D.Double[] points) {
        super(ax, ay, r, childPoints, childRotations, link);

        this.points = new Point2D.Double[points.length];
        for (int i = 0; i < this.points.length; i++)
            this.points[i] = new Point2D.Double(points[i].x + ax, points[i].y + ay);
    }

    private Point2D.Double[] points;

    @Override
    public Point2D.Double[] getPoints() {
        Point2D.Double[] copy = new Point2D.Double[points.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = new Point2D.Double(points[i].x, points[i].y);
        }
        return copy;
    }

    @Override
    public void translate() {
        super.translate();

        for (Point2D.Double point : points)
            point.setLocation(point.x + getDX(), point.y + getDY());
    }

    @Override
    public void rotate() {
        super.rotate();

        double cos = Math.cos(getDR()), sin = Math.sin(getDR());

        for (Point2D.Double point : points) {
            Point2D.Double prime = new Point2D.Double(
                    point.x - getAX(),
                    point.y - getAY());
            point.setLocation(
                    prime.x * cos - prime.y * sin + getAX(),
                    prime.x * sin + prime.y * cos + getAY());
        }
    }

    @Override
    public boolean contains(double x, double y) {
        int[] xPointsPrime = new int[points.length];
        int[] yPointsPrime = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPointsPrime[i] = (int) points[i].x;
            yPointsPrime[i] = (int) points[i].y;
        }

        Polygon p = new Polygon(xPointsPrime, yPointsPrime, points.length);

        return p.contains(x, y);
    }
}
