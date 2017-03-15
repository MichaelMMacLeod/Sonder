package com.github.Sonder.Visual;

public interface Transformable {
    /**
     * Applies the current transformation.
     */
    void transform();

    /**
     * Applies the current translation transformation.
     */
    void translate();

    /**
     * Applies the current rotation transformation.
     */
    void rotate();

    /**
     * Appends the current transformation with a translation.
     */
    void translate(double dx, double dy);

    /**
     * Appends the current transformation with a rotation.
     */
    void rotate(double dr);

    /**
     * @return the angle of rotation in radians.
     */
    double getR();

    /**
     * Sets the anchor of this object to a certain point.
     *
     * Overriding methods MUST call super.setAnchor(dx, dy).
     * @param ax is the x coordinate.
     * @param ay is the y coordinate.
     */
    void setAnchor(double ax, double ay);
}
