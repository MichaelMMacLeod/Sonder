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

    private ArrayList<Outline> objects;

    private Pod player;

    private Linked selected;
    private Linked connectTo;
    private Connection closestConnection;

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
        input.addKey("e");
        input.addKey("s");
        input.addKey("h");
        input.addKey("l");
        input.addKey("c");
    }

    /**
     * Resets the game to its initial state.
     */
    private void restart() {
        objects = new ArrayList<>();

        player = new Pod(0, 0, 0);
        objects.add(player);

        selected = null;
        connectTo = null;
        closestConnection = null;
    }

    /**
     * Calculates logic updates.
     */
    void update() {
        for (Outline o : objects)
            o.transform();

        double mousex = input.getMouseX() - getWidth() / 2 + player.getAX();
        double mousey = input.getMouseY() - getHeight() / 2 + player.getAY();

        if (input.pressed("h"))
            objects.add(new Pod(mousex, mousey, 0));

        if (input.held("a"))
            player.rotate(-Math.PI / 128);
        if (input.held("d"))
            player.rotate(Math.PI / 128);
        if (input.held("w"))
            player.translate(Math.cos(player.getR()), Math.sin(player.getR()));

        boolean updateSelected = input.pressed("mouse");

        for (Outline o : objects) {
            if (updateSelected) {
                if (o.contains(mousex, mousey)) {
                    selected = o;
                    updateSelected = false;
                }
            }
        }

        if (updateSelected) {
            selected = null;
        }

        if (!input.held("mouse")
                && selected != null
                && connectTo != null
                && !selected.hasInChain(connectTo)) {
            connectTo.attachChild(closestConnection, selected);
            selected = null;
            connectTo = null;
        } else if (selected != null
                && connectTo == null) {
            selected.detachFromParent();
        }

        if (selected != null && input.held("mouse")) {
            Camera.shouldDrawNodes = true;

            selected.translate(mousex - selected.getLink().x, mousey - selected.getLink().y);

            double smallestDist = Integer.MAX_VALUE;
            Linked closestChain = selected;
            closestConnection = null;

            for (Linked chain : objects) {
                if (chain != selected) {
                    Connection[] connections = chain.getConnections();
                    Point2D.Double[] connectionPoints = chain.getConnectionPoints();

                    for (int i = 0; i < connectionPoints.length; i++) {
                        double distance =
                                Math.sqrt(
                                        (connectionPoints[i].x - selected.getLink().x) * (connectionPoints[i].x - selected.getLink().x)
                                        + (connectionPoints[i].x - selected.getLink().x) * (connectionPoints[i].x - selected.getLink().x)
                                );
                        if (distance < smallestDist) {
                            closestChain = chain;
                            closestConnection = connections[i];
                            smallestDist = distance;
                        }
                    }
                }
            }

            if (smallestDist < 15) {
                connectTo = closestChain;
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
        Camera.draw(g, getWidth(), getHeight(), objects.toArray(new Outline[0]), player.getAX(), player.getAY());
    }

    /**
     * Returns the initial size of the window.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}