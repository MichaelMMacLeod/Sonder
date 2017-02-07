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

    /**
     * Draws objects on the screen using a graphics object.
     */
    private Camera camera;

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

        // Initialize command factory and controls.

        input = new InputManager(this);
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

    }

    /**
     * Draws objects on the screen.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

    /**
     * Returns the initial size of the window.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}