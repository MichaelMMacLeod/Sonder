package com.github.Sonder.server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;

public class SonderServer {

    private static final int DEFAULT_PORT = 5782;

    private static ArrayList<Player> players;

    private static ServerSocket socket;

    private static boolean serverOn = true;

    public static void main(String[] args) throws IOException {
        log("Starting SonderServer...");

        players = new ArrayList<>();

        socket = new ServerSocket(DEFAULT_PORT);

        if (args.length > 0) {
            try {
                int port = Integer.parseInt(args[0]);
                socket = new ServerSocket(port);
                log("Started server on custom port: " + port);
            } catch (Exception e) {
                log("Error: Port '" + args[0] + "' is not a number");
                log("Stopping Server...");
                System.exit(1);
            }
        } else {
            log("Started server on default port: " + DEFAULT_PORT);
        }

        ConnectionListener connectionListener = new ConnectionListener();
        connectionListener.start();

        InputListener inputListener = new InputListener();
        inputListener.start();
    }

    private static class InputListener extends Thread {
        public void run() {

            Scanner scan = new Scanner(System.in);

            while (serverOn) {
                switch (scan.nextLine()) {
                    case "stop":
                        log("Stopping SonderServer...");
                        serverOn = false;
                        try {
                            socket.close();
                        } catch (IOException e) {
                            log("Error: Couldn't close server properly: " + e);
                        }
                        break;
                    case "list":
                        for (int i = 0; i < players.size(); i++) {
                            log("Player #" + i + ": " + players.get(i));
                        }
                        break;
                    default: break;
                }
            }
        }
    }

    private static class ConnectionListener extends Thread {
        public void run() {
            while (serverOn) {
                try {
                    players.add(new Player(socket.accept()));
                } catch (IOException e) {
                    if (serverOn) {
                        log("Error: Couldn't connect player: " + e);
                    }
                }
            }
        }
    }

    private static class Player extends Thread {

        private Socket socket;

        public Player(Socket socket) {
            this.socket = socket;
            log("New connection: " + socket);
        }

        public String toString() {
            return socket.toString();
        }
    }

    private static void log(String text) {
        System.out.println("[SonderServer] " + text);
    }
}
