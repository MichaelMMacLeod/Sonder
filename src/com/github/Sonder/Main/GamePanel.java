package com.github.Sonder.Main;

import com.github.Sonder.Input.InputManager;
import com.github.Sonder.Visual.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.geom.Point2D;
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

    private ArrayList<Poly> objects;

    private Part player;

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

        player = new Part(
                null,
                new Point2D.Double(0, 0),
                0, 0,
                Color.BLACK,
                false);

        Part wingTop = new Part(
                player,
                new Point2D.Double(),
                new double[] {-60,  0, 30, -30},
                new double[] {-30, -30, 30,  30},
                -15,
                -60,
                Color.BLACK,
                false);
        Part wingBot = new Part(
                player,
                player.getVector(),
                new double[] {-60,  0, 30, -30},
                new double[] {30, 30, -30,  -30},
                -15,
                60,
                Color.BLACK,
                false);
        Part front = new Part(
                player,
                player.getVector(),
                new double[] {-30, 69, -30},
                new double[] {-30, 0, 30},
                63,
                0,
                Color.BLACK,
                false);
        Part engineCenter = new Part(
                player,
                player.getVector(),
                new double[] {-10, 10, 10, -10},
                new double[] {-25, -25, 25, 25},
                -40,
                0,
                Color.BLACK,
                false);
        Part engineTop = new Part(
                wingTop,
                wingTop.getVector(),
                new double[] {-20, 0, -20},
                new double[] {-20, 20, 20},
                -47,
                -50,
                Color.BLACK,
                false);
        Part engineBot = new Part(
                wingTop,
                wingTop.getVector(),
                new double[] {-20, 0, -20},
                new double[] {20, -20, -20},
                -47,
                50,
                Color.BLACK,
                false);

        for (Part part : player.getParts()) {
            objects.add(part);
        }

        Part reference = new Part(null, new Point2D.Double(0, 0), 0, 0, Color.CYAN, false);
        objects.add(reference);
    }

    /**
     * Calculates logic updates.
     */
    void update() {
        Point2D.Double playerAnchor = player.anchorPoint();

        if (input.held("w"))
            player.thrust(0.05);
        if (input.held("a"))
            player.rotate(-Math.PI / 64, playerAnchor.x, playerAnchor.y);
        if (input.held("d"))
            player.rotate(Math.PI / 64, playerAnchor.x, playerAnchor.y);

        player.update();
    }

    /**
     * Draws objects on the screen.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Point2D.Double playerAnchor = player.anchorPoint();
        Camera.draw(g, getWidth(), getHeight(), objects.toArray(new Poly[0]), playerAnchor.x, playerAnchor.y);
    }

    /**
     * Returns the initial size of the window.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}