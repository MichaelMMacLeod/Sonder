package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;

public class Camera {

    private Camera() {}

    private static double zoom = 1;

    public static void draw(
            Graphics g,
            double width,
            double height,
            Poly[] shapes,
            double fx,
            double fy) {
        for (Poly shape : shapes) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(shape.getColor());
            g2.setStroke(new BasicStroke(3));

            double[] xVertices = shape.getXVertices();
            double[] yVertices = shape.getYVertices();

            int vertices = shape.getNumberOfVertices();

            for (int i = 0; i < vertices; i++) {
                xVertices[i] = (xVertices[i] - fx) * zoom + width / 2;
                yVertices[i] = (yVertices[i] - fy) * zoom + height / 2;
            }

            int[] integerXVertices = new int[vertices];
            int[] integerYVertices = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                integerXVertices[i] = (int) xVertices[i];
                integerYVertices[i] = (int) yVertices[i];
            }

            if (shape.isFilled())
                g2.fillPolygon(integerXVertices, integerYVertices, vertices);
            else
                g2.drawPolygon(integerXVertices, integerYVertices, vertices);
        }
    }
}