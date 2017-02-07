package com.github.Sonder.Main;

import com.github.Sonder.Input.InputManager;
import com.github.Sonder.Visual.*;

import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.HashMap;

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

    /**
     * Draws objects on the screen using a graphics object.
     */
    private Camera camera;

    /**
     * Contains all ships.
     */
    private ArrayList<Ship> players;

    /**
     * Contains Projectiles.
     */
    private ArrayList<Moveable> updates;

    /**
     * Binds keyboard input to commands.
     */

    Ship2 tester;

    /**
     * Creates a com.github.Sonder.GamePanelder.Main.GamePanel object.
     *
     * @param width  is the initial width of the window.
     * @param height is the initial height of the window.
     */
    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        restart();

        input = new InputManager(this);

        tester = new Ship2();
        HashMap<String, Ship2.Command> partCommands = new HashMap<>();
        partCommands.put("thrust", () -> {
            tester.vector[0] += 0.1 * Math.cos(tester.getRotation());
            tester.vector[1] += 0.1 * Math.sin(tester.getRotation());
        });
        partCommands.put("rotate counter clockwise", () -> {
            tester.rotate(0.1);
        });
        partCommands.put("rotate clockwise", () -> {
            tester.rotate(-0.1);
        });
        tester.addPart(
                new Part(
                        tester,
                        new Drawn(
                                Drawn.TRIANGLE,
                                new Point2D.Double(-60, 0),
                                30,
                                0,
                                new Point2D.Double(0, 0),
                                Color.BLUE,
                                false),
                        new HashMap<String, Double>(),
                        new Point2D.Double(0, 0),
                        partCommands
                )
        );
        tester.addPart(
                new Part(
                        tester,
                        new Drawn(
                                Drawn.TRIANGLE,
                                new Point2D.Double(-60, 0),
                                30,
                                0,
                                new Point2D.Double(3, 0),
                                Color.BLUE,
                                false),
                        new HashMap<String, Double>(),
                        new Point2D.Double(0, 0),
                        partCommands
                )
        );

        for (Drawn shape : tester.getShapes()) {
            camera.add(shape, true);
        }

        input.addKey("w");
        input.addKey("a");
        input.addKey("d");
    }

    /**
     * Resets the game to its initial state.
     */
    private void restart() {
        camera = new Camera();
        updates = new ArrayList<>();

        camera.add(
                new Drawn(
                        Drawn.SQUARE,
                        new Point2D.Double(),
                        120,
                        0,
                        new Point2D.Double(0, 0),
                        Color.GREEN,
                        false),
                false);
    }

    /**
     * Calculates logic updates.
     */
    public void update() {

        tester.update();

        if (input.held("w"))
            tester.trigger("thrust");
        if (input.held("a"))
            tester.trigger("rotate counter clockwise");
        if (input.held("d"))
            tester.trigger("rotate clockwise");
    }

    /**
     * Draws objects on the screen.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        camera.draw(g, getWidth(), getHeight(), 32);
    }

    /**
     * Returns the initial size of the window.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}