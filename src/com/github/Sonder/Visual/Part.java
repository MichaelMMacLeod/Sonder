package com.github.Sonder.Visual;

import com.github.Sonder.Command.Command;

import java.awt.*;
import java.util.HashMap;

public class Part extends Poly {
    private final HashMap<String, Command> actions;

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
        actions = new HashMap<>();
    }

    public void addAction(String key, Command action) {
        actions.put(key, action);
    }

    boolean hasAction(String key) {
        return actions.containsKey(key);
    }

    void trigger(String key) {
        actions.get(key).apply();
    }
}
