package com.github.Sonder.Visual;

import java.awt.*;

public abstract class Part extends Poly {
    protected PartCollection parent;

    public Part(
            PartCollection parent,
            double[] xverts,
            double[] yverts,
            double x,
            double y,
            double anchorx,
            double anchory,
            Color color,
            boolean isFilled) {
        super(xverts, yverts, x, y, anchorx, anchory, color, isFilled);
        this.parent = parent;
    }

    public abstract void update();
}
