package com.github.Sonder.Visual;

import java.awt.*;

public class Part extends Poly {
    public Part(
            double[] xverts,
            double[] yverts,
            double x,
            double y,
            double anchorx,
            double anchory,
            Color color,
            boolean isFilled) {
        super(xverts, yverts, x, y, anchorx, anchory, color, isFilled);
    }
}
