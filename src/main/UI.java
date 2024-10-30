package main;

import object.OBJ_Gem;
import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_40;
    BufferedImage gemImage;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Gem gem  = new OBJ_Gem();
        gemImage = gem.image;

        //create HUD object

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

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
            //display gems
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(gemImage, -10, gp.tileSize*1 + 14, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.gemNum, 30, gp.tileSize*2);

            //display coordinates
            g2.drawString("x " + gp.player.worldX / gp.tileSize +", y " +
                    gp.player.worldY / gp.tileSize,10, gp.tileSize*3);

            //time
            playTime += (double)1/60;
            g2.drawString("Time " + dFormat.format(playTime), gp.tileSize*11, 66);

            //message
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
}
