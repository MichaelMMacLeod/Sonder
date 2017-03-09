package com.github.Sonder.Main;

import com.github.Sonder.Input.InputManager;
import com.github.Sonder.Visual.*;

import java.awt.Dimension;
import java.awt.Graphics;

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

    private Capsule player;

    private Poly selected;
    private Poly connectTo;
    private int closestNode;

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

        player = new Capsule(0, 0);
        objects.add(player);

        for (int i = -500; i < 500; i += 200) {
            for (int j = -500; j < 500; j += 200) {
                objects.add(new Hull(i, j));
            }
        }

        selected = null;
        connectTo = null;
        closestNode = -1;
    }

    /**
     * Calculates logic updates.
     */
    void update() {
        double mousex = input.getMouseX() - getWidth() / 2 + player.getCenterX();
        double mousey = input.getMouseY() - getHeight() / 2 + player.getCenterY();

        boolean updateSelected = input.pressed("mouse");

        if (!input.held("mouse")
                && selected != null
                && connectTo != null) {
            connectTo.attatch(selected, closestNode);
            selected = null;
            connectTo = null;
        } else if (selected != null
                && connectTo == null) {
            selected.detatch();
        }

        for (Poly poly : objects) {
            if (updateSelected) {
                if (poly.contains(mousex, mousey)) {
                    selected = poly;
                    updateSelected = false;
                }
            }
        }

        if (selected != null && input.held("mouse")) {
            Camera.shouldDrawNodes = true;

            selected.translate(mousex - selected.getXConnector(), mousey - selected.getYConnector());

            double smallestDist = Integer.MAX_VALUE;
            Poly closestPoly = selected;
            closestNode = -1;

            for (Poly poly : objects) {
                if (poly != selected) {
                    double[] xnodes = poly.getXNodes();
                    double[] ynodes = poly.getYNodes();

                    for (int i = 0; i < poly.getNumberOfNodes(); i++) {
                        double distance =
                                Math.sqrt(
                                        (xnodes[i] - selected.getXConnector()) * (xnodes[i] - selected.getXConnector())
                                        + (ynodes[i] - selected.getYConnector()) * (ynodes[i] - selected.getYConnector())
                                );
                        if (distance < smallestDist) {
                            closestPoly = poly;
                            closestNode = i;
                            smallestDist = distance;
                        }
                    }
                }
            }

            if (smallestDist < 15) {
                connectTo = closestPoly;
            } else {
                connectTo = null;
            }
        } else {
            Camera.shouldDrawNodes = false;
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