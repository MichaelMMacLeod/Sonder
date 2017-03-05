package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

final class Transform {
    private Transform() {}

    public static void translate(double dx, double dy, Point2D.Double... points) {
        for (Point2D.Double point : points) {
            point.x += dx;
            point.y += dy;
        }
    }

    public static void rotate(double theta, Point2D.Double... points) {
        double cos = Math.cos(theta), sin = Math.sin(theta);
        for (Point2D.Double point : points) {
            Point2D.Double prime = new Point2D.Double(
                    point.x * cos - point.y * sin,
                    point.x * sin + point.y * cos);
            point.setLocation(prime.x, prime.y);
        }
    }

    public static Point2D.Double[] copy(Point2D.Double[] source) {
        Point2D.Double[] copy = new Point2D.Double[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = new Point2D.Double(source[i].x, source[i].y);
        }
        return copy;
    }
}
