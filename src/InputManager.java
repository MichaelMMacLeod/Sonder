import java.util.ArrayList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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

    public InputManager(JPanel panel) {
        this.panel = panel;

        keyValues = new ArrayList<String>();
        keys = new ArrayList<Boolean>();
        keysChecked = new ArrayList<Boolean>();

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