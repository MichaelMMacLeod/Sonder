package com.github.Sonder.Visual;

import java.util.HashMap;

public class Ship2 {
    private HashMap<String, Double> attributes;

    private Part[] parts;

    public void update() {
        for (Part part : parts) {
            part.update();
        }
    }

    private String[] keys =
            {
                    "rotate counter clockwise key",
                    "rotate clockwise key",
                    "thrust key",
                    "fire key"
            };

    public Ship2(Part[] parts, String[] keys) {
        this.parts = parts;
        this.keys = keys;
    }
}
