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
            double[] xVertices = shape.getXVertices();
            double[] yVertices = shape.getYVertices();

            int vertices = shape.getNumberOfVertices();

            for (int i = 0; i < vertices; i++) {
                xVertices[i] = (xVertices[i] - fx) * zoom + width / 2;
                yVertices[i] = (yVertices[i] - fy) * zoom + height / 2;
            }

            double[] xnodes = shape.getXNodes();
            double[] ynodes = shape.getYNodes();

            int nodes = shape.getNumberOfNodes();

            for (int i = 0; i < nodes; i++) {
                xnodes[i] = (xnodes[i] - fx) * zoom + width / 2;
                ynodes[i] = (ynodes[i] - fy) * zoom + height / 2;
            }

            double xconnector = shape.getXConnector();
            double yconnector = shape.getYConnector();

            xconnector = (xconnector - fx) * zoom + width / 2;
            yconnector = (yconnector - fy) * zoom + height / 2;

            int[] integerXVertices = new int[vertices];
            int[] integerYVertices = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                integerXVertices[i] = (int) xVertices[i];
                integerYVertices[i] = (int) yVertices[i];
            }

            g.setColor(shape.getFill());
            g.fillPolygon(integerXVertices, integerYVertices, vertices);

            g.setColor(shape.getOutline());
            g.drawPolygon(integerXVertices, integerYVertices, vertices);

            if (shouldDrawNodes) {
                g.setColor(Color.LIGHT_GRAY);
                for (int i = 0; i < nodes; i++) {
                    g.drawOval((int) xnodes[i] - 5, (int) ynodes[i] - 5, 10, 10);
                }
                g.fillOval((int) xconnector - 5, (int) yconnector - 5, 10, 10);
            }
        }
    }

    public static void draw(
            Graphics g,
            double width,
            double height,
            Chain[] shapes, // TODO: don't use Chain here, fix inheritance and use Outline.
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

        for (Chain shape : shapes) {
            Point2D.Double[] points = shape.getPoints();

            for (Point2D.Double point : points) {
                point.setLocation(
                        (point.x - fx) * zoom + width / 2,
                        (point.y - fy) * zoom + height / 2);
            }

            Point2D.Double[] connections = shape.getConnectionPoints();

            for (Point2D.Double connection : connections) {
                connection.setLocation(
                        (connection.x - fx) * zoom + width / 2,
                        (connection.y - fy) * zoom + height / 2);
            }

            Point2D.Double link = shape.getLink();

            link.setLocation(
                    (link.x - fx) * zoom + width / 2,
                    (link.y - fy) * zoom + height / 2);

            int[] xPoints = new int[points.length];
            int[] yPoints = new int[points.length];

            for (int i = 0; i < points.length; i++) {
                xPoints[i] = (int) points[i].x;
                yPoints[i] = (int) points[i].y;
            }

            // TODO: use color in Outline class?

            g.setColor(Color.WHITE);
            g.fillPolygon(xPoints, yPoints, points.length);

            g.setColor(Color.LIGHT_GRAY);
            g.drawPolygon(xPoints, yPoints, points.length);

            if (shouldDrawNodes) {
                g.setColor(Color.LIGHT_GRAY);
                for (Point2D.Double connection : connections) {
                    g.drawOval((int) connection.x - 5, (int) connection.y - 5, 10, 10);
                }
                g.fillOval((int) link.x - 5, (int) link.y - 5, 10, 10);
            }
        }
    }
}