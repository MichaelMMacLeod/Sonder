package com.github.Sonder.Visual;

import java.awt.*;

public class Engine extends Part {
    private final double force;

    public Engine(
            PartCollection parent,
            double[] xverts,
            double[] yverts,
            double x,
            double y,
            double anchorx,
            double anchory,
            Color color,
            boolean isFilled,
            double force) {
        super(parent, xverts, yverts, x, y, anchorx, anchory, color, isFilled);
        this.force = force;
    }

    @Override
    public void update() {
        parent.translateVector(force * Math.cos(parent.getRotation()), force * Math.sin(parent.getRotation()));
        double angle = Math.atan((parent.getAnchorY() - center.y)/(parent.getAnchorX() - center.x));
        parent.rotate(angle);
    }
}
