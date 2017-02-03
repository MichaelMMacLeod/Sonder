package com.github.Sonder.server;

public class SonderServer {

    private static final int MS_PER_UPDATE = 10;

    public static void main(String[] args) {
        Sonder game = new Sonder();

        double previous = System.currentTimeMillis();
        double lag = 0;

        // TODO: add a key to exit the application
        while (true) {
            double current = System.currentTimeMillis();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            while (lag >= MS_PER_UPDATE) {
                game.update();
                lag -= MS_PER_UPDATE;
            }
        }
    }
}
