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

        player = new Part(null, 0, 0, Color.BLACK, false);
        Part part2 = new Part(player, 60, 0, Color.GREEN, false);
        Part part3 = new Part(part2, 60, 60, Color.PINK, false);

        for (Part part : player.getParts()) {
            objects.add(part);
        }

        part3.detach();

        Part reference = new Part(null, 0, 0, Color.CYAN, false);
        objects.add(reference);
    }

    /**
     * Calculates logic updates.
     */
    void update() {
        if (input.held("w"))
            player.translate(Math.cos(player.getRotation()), Math.sin(player.getRotation()));
        if (input.held("a"))
            player.rotate(-Math.PI / 64);
        if (input.held("d"))
            player.rotate(Math.PI / 64);
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