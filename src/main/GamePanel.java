package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import entity.Player;
import object.SuperObject;
import tiles.TileManager;
import entity.Entity;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12; // creates a 4x3 screen ratio
    public final int screenWidth = tileSize * maxScreenCol; // currently 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // currently 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 14;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // handles the game loop
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    //Objects
    public SuperObject obj[] = new SuperObject[10];

    // Entities
    public Player player = new Player(this, keyH);
    public Entity enemy[] = new Entity[10];

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improves game performance

        this.addKeyListener(keyH); // adds the key listener to the panel
        this.setFocusable(true); // allows the panel to receive focus
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setMonster();
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // 1. UPDATE: update information, such as character positions
                update();
                // 2. DRAW: draw the screen, with the updated information
                repaint();
                delta--;
            }
        }
    }

    public void update() {

        // Player
        player.update();

        // Enemy
        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i] != null) {
                enemy[i].update();
            }
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Tiles
        tileM.draw(g2);

        // Objects
        for(int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // Enemies
        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i] != null) {
                enemy[i].draw(g2);
            }
        }

        // Players
        player.draw(g2);

        g2.dispose();
    }
}
