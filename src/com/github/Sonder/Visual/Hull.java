package com.github.Sonder.Visual;

import java.awt.*;

public class Hull extends Part {
    public Hull(
            PartCollection parent,
            double[] xverts,
            double[] yverts,
            double x,
            double y,
            double anchorx,
            double anchory,
            Color color,
            boolean isFilled) {
        super(parent, xverts, yverts, x, y, anchorx, anchory, color, isFilled);
    }

    @Override
    public void update() {

    }
}
