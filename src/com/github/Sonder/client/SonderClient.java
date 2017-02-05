package com.github.Sonder.client;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import java.net.Socket;

import java.util.Scanner;

public class SonderClient {

    private static boolean clientOn = true;
    private static Socket socket;
    private static GamePanel gamePanel;
    private static final int MS_PER_UPDATE = 10;

    public static void main(String[] args) throws IOException {
        SonderClient client = new SonderClient();
        client.connect();

        // Start GUI

        System.setProperty("sun.java2d.opengl", "true");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        gamePanel = new GamePanel(width, height, socket);

        SwingUtilities.invokeLater(SonderClient::createAndShowGUI);

        gameLoop();
    }

    private static void gameLoop() {
        double previous = System.currentTimeMillis();
        double lag = 0;

        while (true) {
            double current = System.currentTimeMillis();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            while (lag >= MS_PER_UPDATE) {
                gamePanel.update();
                gamePanel.repaint();
                lag -= MS_PER_UPDATE;
            }
        }
    }

    private void connect() throws IOException {
        Scanner scan = new Scanner(System.in);

        log("Enter IP address of server: ");
        String serverAddress = scan.nextLine();

        log("Enter port number: ");
        int port = scan.nextInt();

        socket = new Socket(serverAddress, port);

        InputListener inputListener = new InputListener();
        inputListener.start();
    }

    private class InputListener extends Thread {

        private ObjectInputStream in;

        public InputListener() throws IOException {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        }

        public void run() {
            while (clientOn) {

            }
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sonder");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(false);

        gamePanel.setBackground(Color.WHITE);

        frame.add(gamePanel);
        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void log(String text) {
        System.out.print("[SonderClient]: " + text);
    }

    private void logln(String text) {
        log(text + "\n");
    }
}
