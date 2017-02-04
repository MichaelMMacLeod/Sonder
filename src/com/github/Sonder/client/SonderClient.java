package com.github.Sonder.client;

import java.io.IOError;
import java.io.IOException;
import java.net.Socket;

import java.util.Scanner;

public class SonderClient {

    private Scanner scan;

    public static void main(String[] args) throws IOException {
        SonderClient client = new SonderClient();
        client.connect();
    }

    public void connect() throws IOException {
        scan = new Scanner(System.in);

        log("Enter IP address of server: ");
        String serverAddres = scan.nextLine();

        log("Enter port number: ");
        int port = scan.nextInt();

        Socket socket = new Socket(serverAddres, port);
    }

    private void log(String text) {
        System.out.print("[SonderClient]: " + text);
    }

    private void logln(String text) {
        log(text + "\n");
    }
}
