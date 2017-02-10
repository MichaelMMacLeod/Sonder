package com.github.Sonder.Visual;

import java.awt.geom.Point2D;
import java.awt.Graphics;

import java.util.ArrayList;

public class Camera {

    private Camera() {}

    private static double zoom = 1;

    public static void draw(Graphics g,
                     double width,
                     double height,
                     Drawn[] shapes,
                     Point2D.Double focus) {
        for (Drawn shape : shapes) {
            g.setColor(shape.getColor());

            double[] xVertices = shape.getVertices()[0];
            double[] yVertices = shape.getVertices()[1];

            int vertices = shape.getNumberOfVertices();

            for (int i = 0; i < vertices; i++) {
                xVertices[i] = (xVertices[i] - focus.x) * zoom + width / 2;
                yVertices[i] = (yVertices[i] - focus.y) * zoom + height / 2;
            }

            int[] integerXVertices = new int[vertices];
            int[] integerYVertices = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                integerXVertices[i] = (int) xVertices[i];
                integerYVertices[i] = (int) yVertices[i];
            }

            if (shape.fillPolygon())
                g.fillPolygon(integerXVertices, integerYVertices, vertices);
            else
                g.drawPolygon(integerXVertices, integerYVertices, vertices);
        }
    }
}