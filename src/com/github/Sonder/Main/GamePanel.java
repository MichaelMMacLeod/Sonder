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

    private final ArrayList<PartCollection> objects;

    private PartCollection player;

    /**
     * Creates a GamePanel object.
     *
     * @param width  is the initial width of the window.
     * @param height is the initial height of the window.
     */
    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        restart();

        // Initialize command factory and controls.

        input = new InputManager(this);

        input.addKey("a");
        input.addKey("w");
        input.addKey("d");
        input.addKey("s");

        objects = new ArrayList<>();

        player = new PartCollection();
        Part hull = new Part(
                new double[] {-30, 30, -30},
                new double[] {-30,  0,  30},
                0,
                0,
                0,
                0,
                Color.BLACK,
                false);
        Part engine = new Part(
                new double[] {-30,  30, 30, -30},
                new double[] {-30, -30, 30,  30},
                -50,
                0,
                30,
                0,
                Color.BLACK,
                false);
        player.addPart(hull);
        player.addPart(engine);
        objects.add(player);
    }

    /**
     * Resets the game to its initial state.
     */
    private void restart() {

    }

    /**
     * Calculates logic updates.
     */
    public void update() {
        if (input.held("a"))
            player.rotate(-Math.PI / 256);
        if (input.held("d"))
            player.rotate(Math.PI / 256);

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