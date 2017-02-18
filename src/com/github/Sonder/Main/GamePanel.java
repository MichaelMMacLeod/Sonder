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

        player = new Hull(
                new Point2D.Double(),
                200,
                200);
//        Part p1 = new Hull(
//                player,
//                3);
//        Part p2 = new Hull(
//                p1,
//                0);
//        Part p4 = new Hull(
//                p2,
//                0
//        );

        objects.addAll(player.getParts());
    }

    /**
     * Calculates logic updates.
     */
    void update() {
        double mousex = input.getMouseX() - getWidth() / 2 + player.getX();
        double mousey = input.getMouseY() - getHeight() / 2 + player.getY();
        if (input.held("mouse")) {
            System.out.println(mousex + " " + mousey);
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