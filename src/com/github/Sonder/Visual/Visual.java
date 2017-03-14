package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

public abstract class Visual {
    /**
     * Creates a visual object defined by a series of points which can be translated and rotated.
     * @param points is the list of points to be copied into this object.
     * @param center is the center of rotation to be copied into this object.
     */
    public Visual(Point2D.Double[] points, Point2D.Double center) {
        this.points = new Point2D.Double[points.length];
        for (int i = 0; i < this.points.length; i++)
            this.points[i] = new Point2D.Double(points[i].x, points[i].y);

        this.center = new Point2D.Double(center.x, center.y);
    }

    private double dx, dy, dt;
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

    /**
     * Applies the current transformation.
     */
    public final void transform() {
        translate();
        rotate();
    }

    /**
     * Appends a translation to the current transformation.
     * @param dx is the distance in the x dimension.
     * @param dy is the distance in the y dimension.
     */
    public final void translate(double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
    }

    /**
     * Appends a rotation to the current transformation.
     * @param dt is the angle in radians.
     */
    public final void rotate(double dt) {
        this.dt += dt;
    }

    /**
     * Applies the current translation and then resets it to zero.
     */
    private void translate() {
        for (Point2D.Double point : points)
            point.setLocation(point.x + dx, point.y + dy);

        center.setLocation(center.x + dx, center.y + dy);

        dx = 0;
        dy = 0;
    }

    /**
     * Applies the current rotation and then resets it to zero.
     */
    private void rotate() {
        double cos = Math.cos(dt), sin = Math.sin(dt);

        for (Point2D.Double point : points) {
            Point2D.Double prime = new Point2D.Double(point.x - center.x, point.y - center.y);
            point.setLocation(prime.x * cos - prime.y * sin, prime.x * sin + prime.y * cos);
        }

        dt = 0;
    }
}
