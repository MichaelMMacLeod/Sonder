package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

/**
 * An Outline holds a series of points which may be connected together to form a polygon.
 */
public interface Outline extends Transformable, Linked {
    /**
     * @return a copy of the points in this object.
     */
    Point2D.Double[] getPoints();

    /**
     * @return true if the point (x, y) is inside the series of points held by this object, false if it isn't.
     */
    boolean contains(double x, double y);
}
