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
//    private final CommandFactory cf;

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

        // Initialize command factory and controls.

        input = new InputManager(this);

//        cf = CommandFactory.init();

        tester = new Ship2();
        HashMap<String, Ship2.Command> partCommands = new HashMap<>();
        partCommands.put("thrust", () -> {
            tester.vector[0] += 0.1 * Math.cos(tester.getRotation());
            tester.vector[1] += 0.1 * Math.sin(tester.getRotation());
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

//        for (Ship player : players) {
//            String[] keys = player.getKeys();
//
//            for (String key : keys)
//                input.addKey(key);
//
//            cf.addCommand(keys[0], () -> player.rotate(false));
//            cf.addCommand(keys[1], () -> player.rotate(true));
//            cf.addCommand(keys[2], player::thrust);
//            cf.addCommand(keys[3], () -> {
//                Drawn d = new Drawn(
//                        Drawn.SQUARE,
//                        player.shape().getPoint(),
//                        5,
//                        player.shape().getRotation(),
//                        new Point2D.Double(0, 0),
//                        player.shape().getColor(),
//                        true);
//                Projectile p = new Projectile(
//                        d,
//                        player.vector(),
//                        10,
//                        player,
//                        0.99);
//                camera.add(p.shape(), false);
//                updates.add(p);
//            });
//        }

        input.addKey("r");

//        cf.listCommands();
    }

    /**
     * Resets the game to its initial state.
     */
    private void restart() {

        // Initialize players

//        players = new ArrayList<>();
//
//        players.add(
//                new Ship(
//                        new Drawn(
//                                Drawn.TRIANGLE,
//                                new Point2D.Double(-60, 0),
//                                30,
//                                0,
//                                new Point2D.Double(0, 0),
//                                Color.BLUE,
//                                false),
//                        0.05,
//                        120,
//                        0.99,
//                        new String[]
//                                {
//                                        "a",
//                                        "d",
//                                        "s",
//                                        "w"
//                                },
//                        0.10));
//
//        players.add(
//                new Ship(
//                        new Drawn(
//                                Drawn.TRIANGLE,
//                                new Point2D.Double(60, 0),
//                                30,
//                                Math.PI,
//                                new Point2D.Double(0, 0),
//                                Color.RED,
//                                false),
//                        0.05,
//                        120,
//                        0.99,
//                        new String[]
//                                {
//                                        "j",
//                                        "l",
//                                        "k",
//                                        "i"
//                                },
//                        0.10));
//
//        players.add(
//                new Ship(
//                        new Drawn(
//                                Drawn.TRIANGLE,
//                                new Point2D.Double(0, 60),
//                                30,
//                                -Math.PI / 2,
//                                new Point2D.Double(0, 0),
//                                Color.BLACK,
//                                false),
//                        0.05,
//                        120,
//                        0.99,
//                        new String[]
//                                {
//                                        "f",
//                                        "h",
//                                        "g",
//                                        "t"
//                                },
//                        0.10));

        // Add objects to the camera and the update list.

        camera = new Camera();
        updates = new ArrayList<>();

//        for (Ship player : players) {
//
//            Drawn[] bars = player.getHealthBar();
//
//            for (Drawn bar : bars)
//                camera.add(bar, false);
//
//            camera.add(player.getShield(), false);
//
//            camera.add(player.shape(), true);
//            updates.add(player);
//        }

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

        if (input.held("r"))
            tester.trigger("thrust");

        // Update all players

//        for (int i = 0; i < players.size(); i++) {
//            Ship player = players.get(i);
//
//            if (player.isAlive()) {
//                String[] keys = player.getKeys();
//
//                if (input.held(keys[0]))
//                    cf.executeCommand(keys[0]);
//                if (input.held(keys[1]))
//                    cf.executeCommand(keys[1]);
//                if (input.held(keys[2]))
//                    cf.executeCommand(keys[2]);
//                if (input.pressed(keys[3]))
//                    cf.executeCommand(keys[3]);
//            } else {
//
//                // Remove dead players.
//
//                camera.removeFocus(player.shape());
//                players.remove(player);
//
//                Drawn[] bars = player.getHealthBar();
//                for (Drawn bar : bars)
//                    camera.removeNonFocus(bar);
//
//                camera.removeNonFocus(player.getShield());
//
//                i--;
//            }
//        }

        // Update all projectiles

//        for (int i = 0; i < updates.size(); i++) {
//            Moveable m = updates.get(i);
//
//            if (m instanceof Projectile) {
//                Projectile p = (Projectile) m;
//
//                for (Ship player : players) {
//                    Ship parent = p.getParent();
//
//                    boolean remove = false;
//
//                    if (player.shieldHitBy(p) && parent != player) {
//                        player.hitShield(10);
//                        remove = true;
//                    }
//
//                    if (player.hitBy(p) && parent != player) {
//                        player.hit(5);
//                        parent.heal(
//                                (parent.getMaxHealth() - parent.getHealth())
//                                        * parent.getPercentHealthOnHit());
//                        remove = true;
//                    }
//
//                    // Remove dead projectiles
//
//                    if (remove) {
//                        camera.removeNonFocus(p.shape());
//                        updates.remove(i);
//                        i--;
//                    }
//                }
//            }
//
//            m.update();
//        }
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