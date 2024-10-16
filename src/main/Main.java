package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the program when the window is closed
        window.setResizable(false); // window cannot be resized
        window.setTitle("2D Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null); // window location not specified, so it becomes centered
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
