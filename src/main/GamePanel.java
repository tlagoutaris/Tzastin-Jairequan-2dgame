package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import entity.Player;
import entity.spin_weapon;
import object.SuperObject;
import tiles.TileManager;
import entity.Entity;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16; // I want the world to be scaled up by 2
    public final int maxScreenRow = 12; // creates a 4x3 screen ratio
    public final int screenWidth = tileSize * maxScreenCol; // currently 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // currently 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 24;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // Thread
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);

    // FPS
    int FPS = 60;

    // System
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    Sound sfx = new Sound();
    Thread gameThread; // handles the game loop
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    // Objects
    public SuperObject[] obj = new SuperObject[100];
    int gemIndex = 0;


    // Entities
    public Player player = new Player(this, keyH);
    public Entity[] enemy = new Entity[100];
    public spin_weapon wep1 = new spin_weapon(this);
    int mobNumber = 1;
    int mobSpawnTime = 0;

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    public final int gameWonState = 4;

    // Flags

    public boolean musicPlaying;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improves game performance

        this.addKeyListener(keyH); // adds the key listener to the panel
        this.setFocusable(true); // allows the panel to receive focus
    }

    public void setupGame() {
        aSetter.setWeapon();
        playMusic(0);
        musicPlaying = true;
        gameState = titleState;
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

        if (gameState == playState) {
            // Player
            player.update();

            // Music
            playMusic(3);

            //test spawn 1 group of enemies
            if(ui.playTime >= mobSpawnTime){
                aSetter.spawnGroupTest(mobNumber);
                mobNumber++;
                if (mobNumber >= 4) {
                    mobNumber = 3;
                }
                mobSpawnTime = (int)ui.playTime + 10;
            }

            // Enemy
            for (int i = 0; i < enemy.length; i++) {
                if (enemy[i] != null) {
                    enemy[i].update();
                    if (enemy[i].life <= 0){
                        final int gemX = enemy[i].worldX;
                        final int gemY = enemy[i].worldY;

                        threadPool.execute(() -> {
                            aSetter.setGem(gemIndex++, gemX, gemY);
                        });
                        gemIndex++;
                        enemy[i] = null;
                    }
                }
            }
        }

        if (gameState == gameOverState) {

            // Music
            playMusic(5);

        }

        if (gameState == gameWonState) {

            // Music
            playMusic(4);

        }

        if (gameState == pauseState) {

            // nothing

        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Title screen
        if (gameState == titleState || gameState == gameOverState || gameState == gameWonState) {

            ui.draw(g2);

        } else {
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

            wep1.draw(g2);

            // Players
            player.draw(g2);

            //UI
            ui.draw(g2);
        }

        g2.dispose();
    }

    public void playMusic(int newSong) {
        if (sound.getCurrentSong() != newSong) {
            sound.stop();
            sound.setFile(newSong);
            sound.play();
            sound.loop();
        }
    }

    public void playSFX(int i) {
        sfx.setFile(i);
        sfx.play();
    }
}
