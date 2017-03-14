package com.github.Sonder.Visual;

abstract class Moveable {
    private double dx, dy, dr;

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
     * Applies the current translation.
     */
    protected abstract void translate();

    /**
     * Applies the current rotation.
     */
    protected abstract void rotate();
}
