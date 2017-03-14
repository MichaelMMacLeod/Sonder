package com.github.Sonder.Visual;

abstract class Moveable {
    /**
     * Creates a Moveable object with an anchor and initial rotation of zero.
     * @param ax is the x coordinate of the anchor.
     * @param ay is the y coordinate of the anchor.
     */
    Moveable(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }

    private double ax, ay, dx, dy, dr, r;

    /**
     * Applies the current transformation.
     */
    public final void transform() {
        translate();
        dx = 0;
        dy = 0;

        rotate();
        dr = 0;
    }

    /**
     * @return the current center of rotation.
     */
    final double getAX() {
        return ax;
    }

    /**
     * @return the current center of rotation.
     */
    final double getAY() {
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
    final double getR() {
        return r;
    }
    /**
     * Sets the anchor of this object to a certain point.
     * @param ax is the x coordinate.
     * @param ay is the y coordinate.
     */
    protected final void setAnchor(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }

    /**
     * Appends a translation to the current transformation.
     * @param dx is the distance in the x dimension.
     * @param dy is the distance in the y dimension.
     */
    protected final void translate(double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
    }

    /**
     * Appends a rotation to the current transformation.
     * @param dt is the angle in radians.
     */
    protected final void rotate(double dt) {
        this.dr += dt;
    }

    /**
     * Applies the current translation to the anchor of this object.
     *
     * Overriding methods MUST call super.translate().
     */
    protected void translate() {
        ax += dx;
        ay += dy;
    }

    /**
     * Applies the current rotation around the anchor of this object.
     *
     * Overriding methods MUST call super.rotate().
     */
    protected void rotate() {
        r += dr;

        // Rotating a point around itself doesn't do anything.
    }
}
