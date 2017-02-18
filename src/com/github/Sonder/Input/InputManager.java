package com.github.Sonder.Input;

import java.util.ArrayList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.JPanel;

public class InputManager {
    private JPanel panel;

    private ArrayList<String> keyValues;
    private ArrayList<Boolean> keys;
    private ArrayList<Boolean> keysChecked;

    private Point mouse;
    private boolean mouseDown;
    private boolean mouseDownChecked;

    public InputManager(JPanel panel) {
        this.panel = panel;

        mouse = new Point();
        mouseDown = false;
        mouseDownChecked = false;

        keyValues = new ArrayList<String>();
        keys = new ArrayList<Boolean>();
        keysChecked = new ArrayList<Boolean>();

        MouseAdapter adapter = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouse = e.getPoint();
            }

            public void mousePressed(MouseEvent e) {
                mouse = e.getPoint();
                mouseDown = true;
            }

            public void mouseDragged(MouseEvent e) {
                mouse = e.getPoint();
                mouseDown = true;
            }

            public void mouseReleased(MouseEvent e) {
                mouse = e.getPoint();
                mouseDown = false;
                mouseDownChecked = false;
            }
        };
        panel.addMouseListener(adapter);
        panel.addMouseMotionListener(adapter);

        Action pressed = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < keyValues.size(); i++) {
                    if (keyValues.get(i).equalsIgnoreCase(
                            e.getActionCommand())) {
                        keys.set(i, true);
                        break;
                    }
                }
            }
        };
        Action released = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < keyValues.size(); i++) {
                    if (keyValues.get(i).equalsIgnoreCase(
                            e.getActionCommand())) {
                        keys.set(i, false);
                        keysChecked.set(i, false);
                        break;
                    }
                }
            }
        };

        panel.getActionMap().put("pressed", pressed);
        panel.getActionMap().put("released", released);
    }

    public double getMouseX() {
        return mouse.getX();
    }
    public double getMouseY() {
        return mouse.getY();
    }

    public boolean pressed(String key) {
        if (key.equalsIgnoreCase("mouse")) {
            if (mouseDown && !mouseDownChecked) {
                mouseDownChecked = true;
                return true;
            }
        } else {
            for (int i = 0; i < keyValues.size(); i++) {
                if (keyValues.get(i).equalsIgnoreCase(key)
                        && keys.get(i)
                        && !keysChecked.get(i)) {
                    keysChecked.set(i, true);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean held(String key) {
        if (key.equalsIgnoreCase("mouse")) {
            return mouseDown;
        } else {
            for (int i = 0; i < keyValues.size(); i++) {
                if (keyValues.get(i).equalsIgnoreCase(key) && keys.get(i)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void addKey(String key) {
        key = key.toUpperCase();

        keyValues.add(key);
        keys.add(false);
        keysChecked.add(false);

        panel.getInputMap().put(KeyStroke.getKeyStroke(key),
                "pressed");
        panel.getInputMap().put(KeyStroke.getKeyStroke("released " + key),
                "released");
    }
}