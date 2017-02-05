package com.github.Sonder.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class InputManager {

    private final JPanel panel;

    private final ArrayList<String> keyValues;
    private final ArrayList<Boolean> keys;
    private final ArrayList<Boolean> keysChecked;

    public InputManager(JPanel panel) {
        this.panel = panel;

        keyValues = new ArrayList<>();
        keys = new ArrayList<>();
        keysChecked = new ArrayList<>();

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

    public HashMap<String, Boolean> getKeyHashMap() {
        HashMap<String, Boolean> keyHashMap = new HashMap<>();
        for (int i = 0; i < keyValues.size(); i++) {
            keyHashMap.put(keyValues.get(i), keys.get(i));
        }
        return keyHashMap;
    }

    public boolean pressed(String key) {
        for (int i = 0; i < keyValues.size(); i++) {
            if (keyValues.get(i).equalsIgnoreCase(key)
                    && keys.get(i)
                    && !keysChecked.get(i)) {

                keysChecked.set(i, true);
                return true;
            }
        }

        return false;
    }

    public boolean held(String key) {
        for (int i = 0; i < keyValues.size(); i++) {
            if (keyValues.get(i).equalsIgnoreCase(key) && keys.get(i)) {
                return true;
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