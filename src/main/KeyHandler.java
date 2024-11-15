package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // NOT USING RIGHT NOW
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Title state
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                if (gp.ui.commandNum - 1 >= 0) {
                    gp.ui.commandNum--;
                    System.out.println(gp.ui.commandNum);
                }
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                if (gp.ui.commandNum + 1 <= gp.ui.maxCommandNum) {
                    gp.ui.commandNum++;
                    System.out.println(gp.ui.commandNum);
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                } else if (gp.ui.commandNum == 1) {
                    //gp.loadGame();
                } else {
                    System.exit(0);
                }
            }
        }

        // Play state
        if (gp.gameState == gp.playState) {

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }

            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }

            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }

            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }

            if (code == KeyEvent.VK_ESCAPE) {
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                }
            }
        }

        // Pause state
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }
}
