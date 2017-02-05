package com.github.Sonder.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

class GamePanel extends JPanel {

    private int width, height;
    private Camera camera;
    private Socket socket;
    private ObjectOutputStream out;
    private InputManager inputManager;

    GamePanel(int width, int height, Socket socket) {
        this.width = width;
        this.height = height;
        this.socket = socket;

        restart();
    }

    void update() {
        try {
            out.writeObject(inputManager.getKeyHashMap());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void restart() {
        inputManager = new InputManager(this);

        inputManager.addKey("a");
        inputManager.addKey("w");
        inputManager.addKey("d");
        inputManager.addKey("s");

        camera = new Camera();

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
