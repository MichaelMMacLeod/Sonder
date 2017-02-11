package com.github.Sonder.Visual;

import java.awt.*;
import java.awt.geom.Point2D;

public class Part extends Drawn {
    public Part(
            double[][] shape,
            Point2D.Double location,
            double size,
            double rotation,
            Point2D.Double rotationAnchor,
            Color color,
            boolean fill) {
        super(shape, location, size, rotation, rotationAnchor, color, fill);
    }
}
