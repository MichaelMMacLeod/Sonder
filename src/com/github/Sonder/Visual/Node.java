package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

public class Node {
    private final Poly source;
    private Poly poly;
    private Point2D.Double point;
    private double rotation;

    public Node(Poly source, Point2D.Double point, double rotation) {
        this.source = source;
        this.point = point;
        this.rotation = rotation;
    }

    public void attatch(Poly poly) {
        poly.translate(point.x - poly.getXConnector(), point.y - poly.getYConnector());
        poly.rotate(rotation - poly.getRotation(), poly.getXConnector(), poly.getYConnector());
        this.poly = poly;
    }
}
