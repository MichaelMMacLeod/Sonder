package com.github.Sonder.Main;

import com.github.Sonder.Input.InputManager;
import com.github.Sonder.Visual.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.geom.Point2D;
import java.util.ArrayList;

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

    private Poly selected;

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

        player = new Hull(
                new Point2D.Double(),
                100,
                100);
        player.setFill(Color.GRAY);
        Part p1 = new Hull(
                player,
                0);
        Part p2 = new Hull(
                p1,
                0);
        Part p3 = new Hull(
                p2,
                0);
        Part p4 = new Hull(
                p3,
                0);

        Part player2 = new Hull(
                new Point2D.Double(),
                0,
                0);
        player2.setFill(Color.GREEN);

        objects.addAll(player.getParts());
        objects.addAll(player2.getParts());

        selected = null;
    }

    /**
     * Calculates logic updates.
     */
    void update() {
        double mousex = input.getMouseX() - getWidth() / 2 + player.getCenterX();
        double mousey = input.getMouseY() - getHeight() / 2 + player.getCenterY();

        boolean updateSelected = input.pressed("mouse");

        for (Poly poly : objects) {
            if (updateSelected) {
                if (poly.contains(mousex, mousey)) {
                    selected = poly;
                    updateSelected = false;
                }
            }
        }

        if (updateSelected) {
            selected = null;
        }

        if (selected != null && input.held("mouse")) {
            selected.translate(mousex - selected.getX(), mousey - selected.getY());
        }
    }

    /**
     * Draws objects on the screen.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Camera.draw(g, getWidth(), getHeight(), objects.toArray(new Poly[0]), player.getCenterX(), player.getCenterY());
    }

    /**
     * Returns the initial size of the window.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}