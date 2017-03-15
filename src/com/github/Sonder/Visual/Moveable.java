package com.github.Sonder.Visual;

abstract class Moveable implements Transformable {
    /**
     * Creates a Moveable object with an anchor and initial rotation of zero.
     * @param ax is the x coordinate of the anchor.
     * @param ay is the y coordinate of the anchor.
     * @param r  is the initial rotation in radians.
     */
    Moveable(double ax, double ay, double r) {
        this.ax = ax;
        this.ay = ay;
        this.r = r;
    }

    private double ax, ay, dx, dy, dr, r;

    /**
     * Applies the current transformation.
     *
     * Overriding methods MUST call super.transform().
     */
    public void transform() {
        translate();
        dx = 0;
        dy = 0;

        rotate();
        dr = 0;
    }

    /**
     * @return the x coordinate of the center of rotation.
     */
    public final double getAX() {
        return ax;
    }

    /**
     * @return the y coordinate of the center of rotation.
     */
    public final double getAY() {
        return ay;
    }
    /**
     * @return the current translation in the x dimension.
     */
    final double getDX() {
        return dx;
    }

    /**
     * @return the current translation in the y dimension.
     */
    final double getDY() {
        return dy;
    }

    /**
     * @return the current rotation in radians.
     */
    final double getDR() {
        return dr;
    }

    /**
     * @return the angle of rotation in radians.
     */
    @Override
    public double getR() {
        return r;
    }

    /**
     * Sets the anchor of this object to a certain point.
     *
     * Overriding methods MUST call super.setAnchor(dx, dy).
     * @param ax is the x coordinate.
     * @param ay is the y coordinate.
     */
    @Override
    public void setAnchor(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }

    /**
     * Appends a translation to the current transformation
     *
     * Overriding methods MUST call super.translate(dx, dy).
     * @param dx is the distance in the x dimension.
     * @param dy is the distance in the y dimension.
     */
    public void translate(double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
    }

    /**
     * Appends a rotation to the current transformation.
     *
     * Overriding methods MUST call super.rotate(dt).
     * @param dt is the angle in radians.
     */
    public void rotate(double dt) {
        this.dr += dt;
    }

    /**
     * Applies the current translation to the anchor of this object.
     *
     * Overriding methods MUST call super.translate().
     */
    public void translate() {
        ax += dx;
        ay += dy;
    }

    public void rotate() {
        r += dr;

        // Rotating a point around itself doesn't do anything.
    }
}
