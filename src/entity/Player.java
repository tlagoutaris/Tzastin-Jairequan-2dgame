package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int gemNum = 0;
    public int levelUpReq;
    public boolean invincible;
    public int iFrames = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.worldWidth / 2;
        worldY = gp.worldHeight / 2;
        direction = "down";

        // Player Status
        maxLife = 10;
        life = maxLife;
        levelUpReq = 1;
        speed = 4;
    }

    public void getPlayerImage() {

        up_idle = setup("/player/player_up_idle");
        up1 = setup("/player/player_up_1");
        up2 = setup("/player/player_up_2");

        down_idle = setup("/player/player_down_idle");
        down1 = setup("/player/player_down_1");
        down2 = setup("/player/player_down_2");

        left1 = setup("/player/player_left_1");
        left2 = setup("/player/player_left_2");

        right1 = setup("/player/player_right_1");
        right2 = setup("/player/player_right_2");

    }

    public void update() {

        boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        if (moving) {

            if (spriteNum == 3) {
                spriteNum = 1; // reset sprite if it was in an up/down idle frame
            }

            // Update direction
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check enemy collision
            int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
            contactMonster(enemyIndex);

            // If collision is false, player can move
            if (!collisionOn) {

                switch (direction) {

                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            //check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else { // for handling idle animations
            if (direction.equalsIgnoreCase("up") || direction.equalsIgnoreCase("down")) {
                spriteNum = 3;
            } else {
                spriteNum = 1; // left or right
            }
        }

        //updates iframes
        if(invincible){
            iFrames++;
            if(iFrames > 60){
                invincible = false;
                iFrames = 0;
            }
        }
    }

    //damage from monsters
    public void contactMonster(int i){
        if(i != 999) {
            if(invincible == false) {
                life -= 1;
                gp.playSFX(1);
                invincible = true;
            }
        }
    }

    public void pickUpObject(int i){
        if(i != 999){
            String objectName = gp.obj[i].name;
            switch (objectName) {
                case "Gem":
                    gemNum++;
                    gp.playSFX(2);
                    gp.obj[i] = null;
                    if(gemNum>=levelUpReq){
                        levelUpReq += levelUpReq*1.25 + 2;
                        gp.ui.showMessage("LEVEL UP!");
                    }

                    //test to end game
                    if(gemNum == 2){
                        gp.gameState = gp.gameWonState;
                    }
                    break;
            }
            gp.obj[i] = null;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":

                if (spriteNum == 1) {
                    image = up1;
                }

                if (spriteNum == 2) {
                    image = up2;
                }

                if (spriteNum == 3) {
                    image = up_idle;
                }

                break;
            case "down":

                if (spriteNum == 1) {
                    image = down1;
                }

                if (spriteNum == 2) {
                    image = down2;
                }

                if (spriteNum == 3) {
                    image = down_idle;
                }

                break;
            case "left":

                if (spriteNum == 1) {
                    image = left1;
                }

                if (spriteNum == 2) {
                    image = left2;
                }

                break;
            case "right":

                if (spriteNum == 1) {
                    image = right1;
                }

                if (spriteNum == 2) {
                    image = right2;
                }

                break;
        }

        //make player transparent
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        //reset transparency for anything else drawn
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }


}
