package com.github.Sonder.Main;

import com.github.Sonder.Input.InputManager;
import com.github.Sonder.Visual.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

class GamePanel extends JPanel {

    /**
     * Initial size of the window. To get the current size, use JPanel's
     * getWidth() and getHeight().
     */
    private final int width;
    private final int height;

    /**
     * Tracks which keys are pressed.
     */
    private final InputManager input;

    private ArrayList<PartCollection> objects;

    private PartCollection player;

    /**
     * Creates a GamePanel object.
     *
     * @param width  is the initial width of the window.
     * @param height is the initial height of the window.
     */
    GamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        restart();

        input = new InputManager(this);

        input.addKey("a");
        input.addKey("w");
        input.addKey("d");
        input.addKey("s");
    }

    /**
     * Resets the game to its initial state.
     */
    private void restart() {
        objects = new ArrayList<>();

        player = new PartCollection();
        Part front = new Part(
                new double[] {-30, 30, -30},
                new double[] {-30,  0,  30},
                0,
                0,
                0,
                0,
                Color.BLACK,
                false);
        Part hull = new Part(
                new double[] {-30,  30, 30, -30},
                new double[] {-30, -30, 30,  30},
                -50,
                0,
                0,
                0,
                Color.BLACK,
                false);
        Part engine = new Part(
                new double[] { -5,   5,  5,  -5},
                new double[] {-25, -25, 25,  25},
                -85,
                0,
                0,
                0,
                Color.BLACK,
                false);
        engine.addAction("thrust", () -> {
            player.thrust(0.1);
        });
        Part wing1 = new Part(
                new double[] {-30, 30, -30},
                new double[] {-30, 30,  30},
                -60,
                -50,
                0,
                0,
                Color.BLACK,
                false);
        Part wing2 = new Part(
                new double[] {-30, 30, -30},
                new double[] {-30, -30,  30},
                -60,
                50,
                0,
                0,
                Color.BLACK,
                false);
        player.addPart(front);
        player.addPart(hull);
        player.addPart(engine);
        player.addPart(wing1);
        player.addPart(wing2);
        objects.add(player);

        PartCollection homeMarker = new PartCollection();
        Part block = new Part(
                new double[] {-300,  300, 300, -300},
                new double[] {-300, -300, 300,  300},
                0,
                0,
                0,
                0,
                Color.GREEN,
                false);
        homeMarker.addPart(block);
        objects.add(homeMarker);

    }

    /**
     * Calculates logic updates.
     */
    void update() {
        if (input.held("a"))
            player.rotate(-Math.PI / 128);
        if (input.held("d"))
            player.rotate(Math.PI / 128);
        if (input.held("w"))
            player.trigger("thrust");

        player.update();
    }

    /**
     * Draws objects on the screen.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // TODO: try creating the allShapes array when you create individual shapes; doing it each time repaint() is called is slow.
        ArrayList<Poly> allShapes = new ArrayList<>();
        for (PartCollection object : objects) {
            Collections.addAll(allShapes, object.getShapes());
        }

        Camera.draw(g, getWidth(), getHeight(), allShapes.toArray(new Poly[0]), player.getLocation());
    }

    /**
     * Returns the initial size of the window.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}