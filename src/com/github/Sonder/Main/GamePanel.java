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

    private Capsule player;

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

        player = new Capsule(0, 0);
        Hull hull = new Hull(100, 100);
        Hull test = new Hull(-500, -500);

        objects.add(player);
        objects.add(hull);
        objects.add(test);

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
            selected.translate(mousex - selected.getCenterX(), mousey - selected.getCenterY());

            // Find the closest part to the selected part

            double x = selected.getCenterX();
            double y = selected.getCenterY();

            double smallestDist = 0;
            Poly closestPoly = selected;

            for (Poly poly : objects) {
                if (selected != poly) {
                    closestPoly = poly;
                    smallestDist = Math.sqrt(
                            (poly.getCenterX() - x) * (poly.getCenterX() - x)
                                    + (poly.getCenterY() - y) * (poly.getCenterY() - y));
                }
            }

            for (Poly poly : objects) {
                if (selected != poly) {
                    double px = poly.getCenterX();
                    double py = poly.getCenterY();

                    double dist = Math.sqrt(
                            (poly.getCenterX() - x) * (poly.getCenterX() - x)
                                    + (poly.getCenterY() - y) * (poly.getCenterY() - y));

                    if (dist < smallestDist) {
                        closestPoly = poly;
                        smallestDist = dist;
                    }
                }
            }

            // Find the smallest distance between nodes of the selected poly and the closest poly

            int closestNode = 0;
            double[] xnodes = selected.getXNodes();
            double[] ynodes = selected.getYNodes();
            int nodes = selected.getNumberOfNodes();

            int closestCNode = 0;
            double[] cxnodes = closestPoly.getXNodes();
            double[] cynodes = closestPoly.getYNodes();
            int cnodes = closestPoly.getNumberOfNodes();

            for (int i = 0; i < nodes; i++) {
                for (int j = 0; j < cnodes; j++) {
                    double dist = Math.sqrt(
                            (cxnodes[j] - xnodes[i]) * (cxnodes[j] - xnodes[i]) +
                                    (cynodes[j] - ynodes[i]) * (cynodes[j] - ynodes[i]));
                    if (dist < smallestDist) {
                        smallestDist = dist;
                        closestNode = i;
                        closestCNode = j;
                    }
                }
            }

            if (smallestDist < 10) {
                selected.moveTo(cxnodes[closestCNode], cynodes[closestCNode]);
            }
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