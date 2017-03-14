package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

public abstract class Outline extends Moveable {
    /**
     * Creates a Outline object defined by a series of points which can be translated and rotated.
     * @param points is the list of points to be copied into this object.
     */
    public Outline(double ax, double ay, Point2D.Double[] points) {
        super(ax, ay);

        this.points = new Point2D.Double[points.length];
        for (int i = 0; i < this.points.length; i++)
            this.points[i] = new Point2D.Double(points[i].x, points[i].y);
    }

    private Point2D.Double[] points;

    /**
     * @return a copy of the points in this object.
     */
    public final Point2D.Double[] getPoints() {
        Point2D.Double[] copy = new Point2D.Double[points.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = new Point2D.Double(points[i].x, points[i].y);
        }
        return copy;
    }

    @Override
    protected void translate() {
        super.translate();

        for (Point2D.Double point : points)
            point.setLocation(point.x + getDX(), point.y + getDY());
    }

    @Override
    protected void rotate() {
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
}
