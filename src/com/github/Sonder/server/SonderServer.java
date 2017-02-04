package com.github.Sonder.server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;

public class SonderServer {

    private static final int DEFAULT_PORT = 5782;

    private static ArrayList<Player> players;

    public static void main(String[] args) throws IOException {
        log("Starting SonderServer...");

        players = new ArrayList<>();

        ServerSocket listener = new ServerSocket(DEFAULT_PORT);

        if (args.length > 0) {
            try {
                int port = Integer.parseInt(args[0]);
                listener = new ServerSocket(port);
                log("Started server on custom port: " + port);
            } catch (Exception e) {
                log("Error: Port '" + args[0] + "' is not a number");
                log("Stopping Server...");
                System.exit(1);
            }
        } else {
            log("Started server on default port: " + DEFAULT_PORT);
        }

        try {
            while (true) {
                players.add(new Player(listener.accept()));
                log("Total connected players: " + players.size());
            }
        } catch(IOException e) {
            log("Error: Couldn't connect player: " + e);
        }
    }

    private static class Player extends Thread {

        private Socket socket;

        public Player(Socket socket) {
            this.socket = socket;
            log("New connection: " + socket);
        }
    }

    private static void log(String text) {
        System.out.println("[SonderServer] " + text);
    }
}
