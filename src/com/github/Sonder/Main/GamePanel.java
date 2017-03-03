package com.github.Sonder.Main;

import com.github.Sonder.Input.InputManager;
import com.github.Sonder.Visual.*;

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

        player = new Capsule(new Point2D.Double(0, 0));
        objects.add(player);

        for (int i = -500; i < 500; i += 200) {
            for (int j = -500; j < 500; j += 200) {
                objects.add(new Hull(new Point2D.Double(i, j)));
            }
        }

        selected = null;
    }

    /**
     * Calculates logic updates.
     */
    void update() {
        double mousex = input.getMouseX() - getWidth() / 2 + player.getCenter().x;
        double mousey = input.getMouseY() - getHeight() / 2 + player.getCenter().y;

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

            selected.translate(mousex - selected.getConnector().x, mousey - selected.getConnector().y);

            double smallestDist = Integer.MAX_VALUE;
            Poly closestPoly = selected;
            int closestNode = -1;

            for (Poly poly : objects) {
                if (poly != selected) {
                    Point2D.Double[] nodes = poly.getNodes();

                    for (int i = 0; i < nodes.length; i++) {
                        double distance =
                                Math.sqrt(
                                        (nodes[i].x - selected.getConnector().x) * (nodes[i].x - selected.getConnector().x)
                                        + (nodes[i].y - selected.getConnector().y) * (nodes[i].y - selected.getConnector().y)
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
                selected.translate(
                        closestPoly.getNodes()[closestNode].x - selected.getConnector().x,
                        closestPoly.getNodes()[closestNode].y - selected.getConnector().y);
                selected.rotate(
                        closestPoly.getNodeRotations()[closestNode] - selected.getRotation(),
                        selected.getConnector().x, selected.getConnector().y);
                Poly.createRelationship(closestPoly, closestNode, selected);
            } else {
                Poly.removeRelationship(closestPoly, selected);
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
        Camera.draw(g, getWidth(), getHeight(), objects.toArray(new Poly[0]), player.getCenter().x, player.getCenter().y);
    }

    /**
     * Returns the initial size of the window.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}