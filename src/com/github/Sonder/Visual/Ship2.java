package com.github.Sonder.Visual;

import java.util.ArrayList;
import java.util.HashMap;

public class Ship2 {
    private final HashMap<String, ArrayList<Command>> triggers;

    private ArrayList<Part> parts;

    public final double[] vector;

    private double rotation;

    public Drawn[] getShapes() {
        ArrayList<Drawn> shapes = new ArrayList<>();
        for (Part part : parts) {
            shapes.add(part.getShape());
        }
        return shapes.toArray(new Drawn[0]);
    }
    public double getRotation() {
        return rotation;
    }

    public void move() {
        for (Part part : parts) {
            part.getShape().translate(vector[0], vector[1]);
        }
    }

    public void update() {
        move();
    }

    public void trigger(String key) {
        for (Command command : triggers.get(key)) {
            command.apply();
        }
    }

    public void addPart(Part part) {
        parts.add(part);

        for (String key : part.getKeys()) {
            if (!triggers.containsKey(key)) {
                triggers.put(key, new ArrayList<>());
            }
            triggers.get(key).add(part.action(key));
        }
    }
    public Ship2() {
        triggers = new HashMap<>();

        this.parts = new ArrayList<>();

        vector = new double[2];
        rotation = 0;
    }

    public interface Command {
        void apply();
    }
}
