package com.github.Sonder.Visual;

import java.awt.*;

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
}