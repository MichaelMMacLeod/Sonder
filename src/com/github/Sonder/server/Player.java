package com.github.Sonder.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Player extends Thread {

    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Ship ship;

    public Player(Socket socket) throws IOException {
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

        start();
    }

    public void run() {
        while (true) {
            try {
                HashMap<String, Boolean> keys = (HashMap<String, Boolean>) in.readObject();
                System.out.println(keys);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error: Couldn't get user input from " + this + ": " + e);
            }
        }
    }

    public String toString() {
        return socket.toString();
    }
}
