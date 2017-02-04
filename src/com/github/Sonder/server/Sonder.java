//package com.github.Sonder.server;
//
//import java.net.Socket;
//import java.util.ArrayList;
//
//public class Sonder extends Thread {
//
//    private static final int MS_PER_UPDATE = 10;
//
//    private ArrayList<Socket> players;
//
//    public void run() {
//        double previous = System.currentTimeMillis();
//        double lag = 0;
//
//        while (true) {
//            double current = System.currentTimeMillis();
//            double elapsed = current - previous;
//            previous = current;
//            lag += elapsed;
//
//            while (lag >= MS_PER_UPDATE) {
//                update();
//                lag -= MS_PER_UPDATE;
//            }
//        }
//    }
//
//    public void connectPlayer(Socket player) {
//
//    }
//
//    private void update() {
//
//    }
//}
