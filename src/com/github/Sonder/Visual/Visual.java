package com.github.Sonder.Visual;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class Visual {
    private double dx, dy, dt;
    private Point2D.Double center;
    private Line2D.Double[] lines;

    /**
     * @return a copy of the lines in this object.
     */
    public final Line2D.Double[] getLines() {
        Line2D.Double[] copy = new Line2D.Double[lines.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = new Line2D.Double(lines[i].x1, lines[i].y1, lines[i].x2, lines[i].y2);
        }
        return copy;
    }

    /**
     * Creates a visual object defined by a series of lines which can be translated and rotated.
     * @param lines  is the list of lines to be copied into this object.
     * @param center is the center of rotation to be copied into this object.
     */
    public Visual(Line2D.Double[] lines, Point2D.Double center) {
        this.lines = new Line2D.Double[lines.length];
        for (int i = 0; i < this.lines.length; i++)
            this.lines[i] = new Line2D.Double(lines[i].x1, lines[i].y1, lines[i].x2, lines[i].y2);

        this.center = new Point2D.Double(center.x, center.y);
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
     * Applies the current translation and then resets it to zero.
     */
    private void translate() {
        for (Line2D.Double line : lines) {
            line.setLine(
                    line.x1 + dx, line.y1 + dy,
                    line.x2 + dx, line.y2 + dy);
        }

        center.setLocation(center.x + dx, center.y + dy);

        dx = 0;
        dy = 0;
    }

    /**
     * Appends a rotation to the current transformation.
     * @param dt is the angle in radians.
     */
    public final void rotate(double dt) {
        this.dt += dt;
    }

    /**
     * Applies the current rotation and then resets it to zero.
     */
    private void rotate() {
        double cos = Math.cos(dt), sin = Math.sin(dt);

        for (Line2D.Double line : lines) {
            Line2D.Double prime = new Line2D.Double(
                    line.x1 - center.x, line.y1 - center.y,
                    line.x2 - center.x, line.y2 - center.y);
            line.setLine(
                    prime.x1 * cos - prime.y1 * sin + center.x, prime.x1 * sin + prime.y1 * cos + center.y,
                    prime.x2 * cos - prime.y2 * sin + center.x, prime.x2 * sin + prime.y2 * cos + center.y);
        }

        dt = 0;
    }
}
