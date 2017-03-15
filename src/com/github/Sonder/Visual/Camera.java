package com.github.Sonder.Visual;

import com.github.Sonder.Visual.Parts.Poly;

import java.awt.*;
import java.awt.geom.Point2D;

public class Camera {

    private Camera() {}

    private static double zoom = 1;

    private static int gridSize = 400;

    public static boolean shouldDrawNodes = false;

    public static void draw(
            Graphics g,
            double width,
            double height,
            Outline[] shapes, // TODO: don't use Chain here, fix inheritance and use Part.
            double fx,
            double fy) {
        int offsetx = -(int) (fy % gridSize);
        int offsety = -(int) (fx % gridSize);

        g.setColor(Color.LIGHT_GRAY);

        for (int i = offsetx; i < height; i += gridSize) {
            g.drawLine(0, i, (int) width, i);
        }
        for (int i = offsety; i < width; i += gridSize) {
            g.drawLine(i, 0, i, (int) height);
        }

        for (Outline shape : shapes) {
            Point2D.Double[] points = shape.getPoints();

            for (Point2D.Double point : points) {
                point.setLocation(
                        (point.x - fx) * zoom + width / 2,
                        (point.y - fy) * zoom + height / 2);
            }

            int[] xPoints = new int[points.length];
            int[] yPoints = new int[points.length];

            for (int i = 0; i < points.length; i++) {
                xPoints[i] = (int) points[i].x;
                yPoints[i] = (int) points[i].y;
            }

            // TODO: use color in Part class?

            g.setColor(Color.WHITE);
            g.fillPolygon(xPoints, yPoints, points.length);

            g.setColor(Color.LIGHT_GRAY);
            g.drawPolygon(xPoints, yPoints, points.length);
        }
    }
}