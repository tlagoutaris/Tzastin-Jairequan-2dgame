package main;

import object.OBJ_Gem;
import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics g2;
    Font arial_40;
    BufferedImage gemImage;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    // Dimensions of UI overlay

    // bottom bar
    int bottom_bar_x;
    int bottom_bar_y;
    int bottom_bar_width;
    int bottom_bar_height;

    // left bar
    int left_bar_x;
    int left_bar_y;
    int left_bar_width;
    int left_bar_height;

    // right bar
    int right_bar_x;
    int right_bar_y;
    int right_bar_width;
    int right_bar_height;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Gem gem  = new OBJ_Gem(gp);
        gemImage = gem.image;

        //create HUD object
        SuperObject heart = new OBJ_Heart(gp);
        heart_blank = heart.image;
        heart_half = heart.image2;
        heart_full = heart.image3;

        setUIOverlayValues();
    }

    public void setUIOverlayValues() {
        // Dimensions of UI overlay

        // bottom bar
        bottom_bar_x = 0;
        bottom_bar_y = gp.screenHeight - (gp.tileSize * 3);
        bottom_bar_width = gp.screenWidth;
        bottom_bar_height = gp.tileSize * 3;

        // left bar
        left_bar_x = 0;
        left_bar_y = 0;
        left_bar_width = gp.tileSize * 2;
        left_bar_height = gp.screenHeight;

        // right bar
        right_bar_x = gp.screenWidth - (gp.tileSize * 2);
        right_bar_y = 0;
        right_bar_width = gp.tileSize * 2;
        right_bar_height = gp.screenHeight;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState) {

            if(gameFinished){

                String text;
                int textLength;
                int x;
                int y;

                text = "You won";
                textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
                g2.setFont(arial_40);
                g2.setColor(Color.yellow);

                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2 - (gp.tileSize*3);
                g2.drawString(text,x,y);

                text = "Your time is " + dFormat.format(playTime);
                textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
                g2.setFont(arial_40);
                g2.setColor(Color.yellow);

                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2 - (gp.tileSize*2);
                g2.drawString(text,x,y);

                gp.gameThread = null;
            }else{

                // UI Overlay
                drawUIOverlay();

                // draw hearts, can be added to each game state when they are added
                drawHearts();

                // display gems
                g2.setFont(arial_40);
                g2.setColor(Color.white);
                g2.drawImage(gemImage, left_bar_width, bottom_bar_y + gp.tileSize + OBJ_Gem.height, gp.tileSize, gp.tileSize, null);
                g2.drawString("x " + gp.player.gemNum, left_bar_width + gp.tileSize, bottom_bar_y + (gp.tileSize * 2));


                // display coordinates
                // g2.drawString("x " + gp.player.worldX / gp.tileSize +", y " + gp.player.worldY / gp.tileSize,10, gp.tileSize*3);


                // time
                playTime += (double)1/60;
                g2.drawString("Time " + dFormat.format(playTime), gp.tileSize*11, bottom_bar_y + gp.tileSize);


                // message
                if(messageOn){
                    g2.setFont(g2.getFont().deriveFont(30f));
                    g2.drawString(message, gp.tileSize / 2, gp.tileSize *5);

                    messageCounter++;

                    if(messageCounter > 120){
                        messageCounter = 0;
                        messageOn = false;
                    }
                }

            }
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawHearts(){

        int x = gp.screenWidth/2 - (gp.player.maxLife/5 * gp.tileSize) - 10;
        int y = bottom_bar_y + gp.tileSize * 2;
        int i = 0;

        // draw blank heart
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // reset
        x = gp.screenWidth/2 - (gp.player.maxLife/5 * gp.tileSize) - 10;
        y = bottom_bar_y + gp.tileSize * 2;
        i = 0;

        // draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    public void drawBottomBar() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(bottom_bar_x, bottom_bar_y, bottom_bar_width, bottom_bar_height);
    }

    public void drawLeftBar() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(left_bar_x, left_bar_y, left_bar_width, left_bar_height);
    }

    public void drawRightBar() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(right_bar_x, right_bar_y, right_bar_width, right_bar_height);
    }

    public void drawInventory() {
        g2.setFont(arial_40);
        g2.setColor(Color.yellow);
        g2.drawString("Inventory", left_bar_width, bottom_bar_y + gp.tileSize);
    }

    public void drawUIOverlay() {

        drawBottomBar();
        drawLeftBar();
        drawRightBar();
        drawInventory();

    }

    public void drawPauseScreen() {

        String pause_text = "PAUSED";
        int pause_text_length = textWidth(pause_text);
        int x = (gp.screenWidth / 2) - pause_text_length / 2;
        int y = gp.screenHeight / 2;

        g2.drawString(pause_text, x, y);
    }

    public int textWidth(String text) {
        return (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    }
}