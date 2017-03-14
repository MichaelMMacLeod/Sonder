package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

public abstract class Outline extends Moveable {
    /**
     * Creates a Outline object defined by a series of points which can be translated and rotated.
     * @param points is the list of points to be copied into this object.
     * @param center is the center of rotation to be copied into this object.
     */
    public Outline(Point2D.Double[] points, Point2D.Double center) {
        this.points = new Point2D.Double[points.length];
        for (int i = 0; i < this.points.length; i++)
            this.points[i] = new Point2D.Double(points[i].x, points[i].y);

        this.center = new Point2D.Double(center.x, center.y);
    }

    private Point2D.Double center;
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

    /**
     * Sets the center of rotation to an (x, y) coordinate.
     * @param x is the x coordinate.
     * @param y is the y coordinate.
     */
    public final void setCenter(double x, double y) {
        center.setLocation(x, y);
    }

    @Override
    protected void translate() {
        for (Point2D.Double point : points)
            point.setLocation(point.x + getDX(), point.y + getDY());

        center.setLocation(center.x + getDX(), center.y + getDY());
    }

    @Override
    protected void rotate() {
        double cos = Math.cos(getDR()), sin = Math.sin(getDR());

        for (Point2D.Double point : points) {
            Point2D.Double prime = new Point2D.Double(point.x - center.x, point.y - center.y);
            point.setLocation(prime.x * cos - prime.y * sin, prime.x * sin + prime.y * cos);
        }
    }
}
