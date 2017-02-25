package com.github.Sonder.Main;

import com.github.Sonder.Input.InputManager;
import com.github.Sonder.Visual.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
        Hull test1 = new Hull(100, 100);
        Hull test2 = new Hull(-500, -500);
        Hull test3 = new Hull(200, 200);
        Hull test4 = new Hull(40, 80);

        objects.add(player);
        objects.add(test1);
        objects.add(test2);
        objects.add(test3);
        objects.add(test4);

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
            Camera.shouldDrawNodes = true;

            selected.translate(mousex - selected.getCenterX(), mousey - selected.getCenterY());

            double smallestDist = Integer.MAX_VALUE;
            Poly closestPoly = selected;
            int closestNode = -1;

            for (Poly poly : objects) {
                if (poly != selected) {
                    double[] xnodes = poly.getXNodes();
                    double[] ynodes = poly.getYNodes();

                    for (int i = 0; i < poly.getNumberOfNodes(); i++) {
                        double distance =
                                Math.sqrt(
                                        (xnodes[i] - selected.getCenterX()) * (xnodes[i] - selected.getCenterX())
                                        + (ynodes[i] - selected.getCenterY()) * (ynodes[i] - selected.getCenterY())
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
                Poly overlapTest = new Poly(
                        selected.getXVertices(),
                        selected.getYVertices(),
                        selected.getNumberOfVertices(),
                        selected.getCenterX(),
                        selected.getCenterY(),
                        selected.getCenterX(),
                        selected.getCenterY(),
                        selected.getOutline(),
                        selected.getFill(),
                        selected.getXNodes(),
                        selected.getYNodes(),
                        selected.getNodeRotations(),
                        selected.getNumberOfNodes(),
                        selected.getXConnector(),
                        selected.getYConnector());

                overlapTest.translate(
                        closestPoly.getXNodes()[closestNode] - selected.getXConnector(),
                        closestPoly.getYNodes()[closestNode] - selected.getYConnector());
                overlapTest.rotate(
                        closestPoly.getNodeRotations()[closestNode] - selected.getRotation(),
                        selected.getCenterX(), selected.getCenterY());

                boolean overlaps = false;

                for (Poly poly : objects) {
                    if (overlapTest.contains(poly.getCenterX(), poly.getCenterY())) {
                        overlaps = true;
                    }
                }

                if (!overlaps) {
                    selected.translate(
                            closestPoly.getXNodes()[closestNode] - selected.getXConnector(),
                            closestPoly.getYNodes()[closestNode] - selected.getYConnector());
                    selected.rotate(
                            closestPoly.getNodeRotations()[closestNode] - selected.getRotation(),
                            selected.getCenterX(), selected.getCenterY());
                }
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