package com.github.Sonder.Main;

import com.github.Sonder.Input.InputManager;
import com.github.Sonder.Visual.*;

import java.awt.geom.Point2D;
import java.awt.Color;
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

    private ArrayList<PartCollection> objects;

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
                Drawn.TRIANGLE,
                new Point2D.Double(0, 0),
                30,
                0,
                new Point2D.Double(0, 0),
                Color.RED,
                true);
        Part engine = new Part(
                Drawn.SQUARE,
                new Point2D.Double(-50, 0),
                30,
                0,
                new Point2D.Double(0, 0),
                Color.ORANGE,
                true);
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

        // TODO: try creating the allShapes array when you create individual shapes; doing it each time repaint() is called is slow.
        ArrayList<Drawn> allShapes = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            Drawn[] collectionShapes = objects.get(i).getShapes();
            for (int j = 0; j < collectionShapes.length; j++) {
                allShapes.add(collectionShapes[j]);
            }
        }

        Camera.draw(g, getWidth(), getHeight(), allShapes.toArray(new Drawn[0]), player.getLocation());
    }

    /**
     * Returns the initial size of the window.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}