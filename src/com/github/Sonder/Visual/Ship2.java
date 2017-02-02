package com.github.Sonder.Visual;

import java.util.ArrayList;
import java.util.HashMap;

public class Ship2 {
    private HashMap<String, ArrayList<Command>> triggers;

    private Part[] parts;

    public final double[] vector;

    public void move() {
        for (Part part : parts) {
            part.getShape().translate(vector[0], vector[1]);
        }
    }

    public void update() {
        move();

        for (Part part : parts) {
            part.update();
        }
    }

    public void trigger(String key) {
        for (Command command : triggers.get(key)) {
            command.apply();
        }
    }

    public Ship2(Part[] parts) {
        this.parts = parts;

        vector = new double[2];

        triggers.put("thrust", new ArrayList<>());
        triggers.put("rotate counter clockwise", new ArrayList<>());
        triggers.put("rotate clockwise", new ArrayList<>());
        triggers.put("turrets", new ArrayList<>());

        for (Part part : parts) {
            for (String key : part.getKeys()) {
                triggers.get(key).add(part.action(key));
            }
        }
    }

    interface Command {
        void apply();
    }
}
