package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

public final class Transform {
    private Transform() {}

    public static Transformation xtranslation = (point, dx) -> point.x += dx;
    public static Transformation ytranslation = (point, dy) -> point.y += dy;
    public static Transformation rotation = (point, theta) -> {
        double cos = Math.cos(theta), sin = Math.sin(theta);
        point = new Point2D.Double(
                point.x * cos - point.y * sin,
                point.x * sin + point.y * cos);
    };

    public static void apply(Transformation t, double value, Point2D.Double... points) {
        for (Point2D.Double point : points)
            t.calculate(point, value);
    }

    @FunctionalInterface
    interface Transformation {
        void calculate(Point2D.Double point, double value);
    }
}
