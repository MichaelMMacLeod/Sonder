package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

public class Node {
    public final Poly source;
    public Poly poly;
    public Point2D.Double point;
    public double rotation;

    public Node(Poly source, Point2D.Double point, double rotation) {
        this.source = source;
        this.point = point;
        this.rotation = rotation;
    }

    public void detatch() {
        poly = null;
    }

    public void attatch(Poly poly) {
        poly.translate(point.x - poly.getXConnector(), point.y - poly.getYConnector());
        poly.rotate(rotation - poly.getRotation(), poly.getXConnector(), poly.getYConnector());
        this.poly = poly;
    }

    public void translate(double dx, double dy) {
        point.x += dx;
        point.y += dy;

        if (poly != null) {
            poly.translate(dx, dy);
        }
    }

    public void rotate(double theta, double x, double y) {
        rotation += theta;

        double cos = Math.cos(theta), sin = Math.sin(theta);

        Point2D.Double p = new Point2D.Double(point.x, point.y);

        point.x -= x;
        point.y -= y;

        p.x = point.x * cos - point.y * sin;
        p.y = point.x * sin + point.y * cos;

        point.x = p.x + x;
        point.y = p.y + y;

        if (poly != null) {
            poly.rotate(theta, x, y);
        }
    }
}
