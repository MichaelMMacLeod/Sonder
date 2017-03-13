package com.github.Sonder.Visual;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class Visual {
    private double dx, dy, dt;
    private Point2D.Double center;
    private Line2D.Double[] lines;

    public final Line2D.Double[] getLines() {
        Line2D.Double[] copy = new Line2D.Double[lines.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = new Line2D.Double(lines[i].x1, lines[i].y1, lines[i].x2, lines[i].y2);
        }
        return copy;
    }

    public Visual(Point2D.Double center, Line2D.Double[] lines) {
        this.center = new Point2D.Double(center.x, center.y);

        this.lines = new Line2D.Double[lines.length];
        for (int i = 0; i < this.lines.length; i++)
            this.lines[i] = new Line2D.Double(lines[i].x1, lines[i].y1, lines[i].x2, lines[i].y2);
    }

    public void update() {
        translate();
        rotate();
    }

    public final void translate(double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
    }

    private void translate() {
        for (Line2D.Double line : lines) {
            line.setLine(
                    line.x1 + dx, line.y1 + dy,
                    line.x2 + dx, line.y2 + dy);
        }

        dx = 0;
        dy = 0;
    }

    public final void rotate(double dt) {
        this.dt += dt;
    }

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
