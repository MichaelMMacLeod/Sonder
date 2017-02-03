package com.github.Sonder.server;

import java.io.IOException;
import java.net.ServerSocket;

public class SonderServer {

    private static final int DEFAULT_PORT = 5782;

    public static void main(String[] args) throws IOException {
        log("Starting SonderServer...");

        Sonder game = new Sonder();
        game.start();

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
                game.connectPlayer(listener.accept());
            }
        } catch(IOException e) {

        }
    }

    private static void log(String text) {
        System.out.println("SonderServer: " + text);
    }
}
