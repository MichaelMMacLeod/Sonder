package com.github.Sonder.client;

import java.io.*;

import java.net.Socket;

import java.util.HashMap;
import java.util.Scanner;

public class SonderClient {

    private static boolean clientOn = true;

    private static Socket socket;

    public static void main(String[] args) throws IOException {
        SonderClient client = new SonderClient();
        client.connect();
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
        private ObjectOutputStream out;

        public InputListener() throws IOException {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
        }

        public void run() {
            while (clientOn) {
                HashMap<String, Object> output = new HashMap<String, Object>();
                output.put("keys", new boolean[]
                        {
                                true,
                                false,
                                false,
                                false
                        });
                try {
                    out.writeObject(output);
                } catch (IOException e) {
                    log("Error: Couldn't write object to server: " + e);
                }

            }
        }
    }

    private void log(String text) {
        System.out.print("[SonderClient]: " + text);
    }

    private void logln(String text) {
        log(text + "\n");
    }
}
