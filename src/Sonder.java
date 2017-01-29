import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Sonder {

    private static GamePanel gamePanel;
    private static final int MS_PER_UPDATE = 10;
    private static boolean on = true;

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        gamePanel = new GamePanel(width, height);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        gameLoop();
    }

    private static void gameLoop() {
        double previous = System.currentTimeMillis();
        double lag = 0;

        while (on) {
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

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sonder");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(false);

        gamePanel.setBackground(Color.WHITE);

        frame.add(gamePanel);
        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}