import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12; // creates a 4x3 screen ratio
    final int screenWidth = tileSize * maxScreenCol; // currently 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // currently 576 pixels

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improves game performance
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while (gameThread != null) {

            System.out.println("The game loop is running");

            // 1. UPDATE: update information such as character positions
            // 2. DRAW: draw the screen

        }
    }
}
