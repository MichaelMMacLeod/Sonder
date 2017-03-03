package com.github.Sonder.Visual;

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
            Poly[] shapes,
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

        for (Poly shape : shapes) {
            Point2D.Double[] points = shape.getPoints();

            for (int i = 0; i < points.length; i++) {
                points[i].x = (points[i].x - fx) * zoom + width / 2;
                points[i].y = (points[i].y - fy) * zoom + height / 2;
            }

            Point2D.Double[] nodes = shape.getNodes();

            for (int i = 0; i < nodes.length; i++) {
                nodes[i].x = (nodes[i].x - fx) * zoom + width / 2;
                nodes[i].y = (nodes[i].y - fy) * zoom + height / 2;
            }

            Point2D.Double connector = shape.getConnector();

            connector.x = (connector.x - fx) * zoom + width / 2;
            connector.y = (connector.y - fy) * zoom + height / 2;

            int[] intXPoints = new int[points.length];
            int[] intYPoints = new int[points.length];

            for (int i = 0; i < points.length; i++) {
                intXPoints[i] = (int) points[i].x;
                intYPoints[i] = (int) points[i].y;
            }

            g.setColor(shape.getFill());
            g.fillPolygon(intXPoints, intYPoints, points.length);

            g.setColor(shape.getOutline());
            g.drawPolygon(intXPoints, intYPoints, points.length);

            if (shouldDrawNodes) {
                g.setColor(Color.LIGHT_GRAY);
                for (int i = 0; i < nodes.length; i++) {
                    g.drawOval((int) nodes[i].x - 5, (int) nodes[i].y - 5, 10, 10);
                }
                g.fillOval((int) connector.x - 5, (int) connector.y - 5, 10, 10);
            }
        }
    }
}