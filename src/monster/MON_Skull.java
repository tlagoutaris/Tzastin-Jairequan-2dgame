package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

public class MON_Skull extends Entity {

    //BufferedImage peaceful_idle, peaceful_up_left, peaceful_up_right, peaceful_down_left, peaceful_down_right;
    //BufferedImage alerted_idle, alerted_up_left, alerted_up_right, alerted_down_left, alerted_down_right;

    //String state = "peaceful";

    boolean invincible = false;
    int invincibleCounter = 0;


    public MON_Skull(GamePanel gp) {
        super(gp);

        type = 1;
        name = "Skull";
        speed = 1;
        maxLife = 2;
        life = maxLife;
        direction = "down";

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        /*peaceful_idle = setup("/monster/enemy_skull_peaceful_idle");

        peaceful_up_left = setup("/monster/enemy_skull_peaceful_up_left");
        peaceful_up_right = setup("/monster/enemy_skull_peaceful_up_right");
        peaceful_down_left = setup("/monster/enemy_skull_peaceful_down_left");
        peaceful_down_right = setup("/monster/enemy_skull_peaceful_down_right");

        alerted_idle = setup("/monster/enemy_skull_alerted_idle");

        alerted_up_left = setup("/monster/enemy_skull_alerted_up_left");
        alerted_up_right = setup("/monster/enemy_skull_alerted_up_right");
        alerted_down_left = setup("/monster/enemy_skull_alerted_down_left");
        alerted_down_right = setup("/monster/enemy_skull_alerted_down_right");*/

        up_idle = setup("/monster/enemy_skull_peaceful_idle");
        up1 = setup("/monster/enemy_skull_peaceful_up_left");
        up2 = setup("/monster/enemy_skull_peaceful_up_right");

        down_idle = setup("/monster/enemy_skull_peaceful_idle");
        down1 = setup("/monster/enemy_skull_peaceful_down_left");
        down2 = setup("/monster/enemy_skull_peaceful_down_right");

        left1 = setup("/monster/enemy_skull_peaceful_down_left");
        left2 = setup("/monster/enemy_skull_peaceful_up_left");

        right1 = setup("/monster/enemy_skull_peaceful_down_right");
        right2 = setup("/monster/enemy_skull_peaceful_up_right");
    }

    public void update(){
        collisionOn = false;
        /*gp.cChecker.checkTile(this);
        gp.cChecker.checkEntity(this, gp.enemy);*/
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        solidArea.x = worldX;
        solidArea.y = worldY;
        //System.out.println("mon " + this.solidArea.x + " " + this.solidArea.y);
        if (this.solidArea.intersects(gp.wep1.solidArea)){
            if(invincible == false) {
                life -= gp.wep1.damage;
                System.out.println("hit");
                invincible = true;
            }

        }

        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter == 30) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(this.type == 1 && contactPlayer){

            if (gp.player.life <= 0) {
                gp.gameState = gp.gameOverState;
            }

            if(!gp.player.invincible){
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        //moves to player
        if(!contactPlayer){

            //move horizonatally
            if(gp.player.worldX > worldX){
                worldX += speed;
                direction = "right";
            } else if(gp.player.worldX < worldX){
                worldX -= speed;
                direction = "left";
            }

            //move vertically
            if(gp.player.worldY > worldY){
                worldY += speed;
                direction = "down";
            }else if(gp.player.worldY < worldY){
                worldY -= speed;
                direction = "up";
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

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

            if(invincible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            //reset transparency for anything else drawn
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    public void setAction() {

        /**actionLockCounter ++;

        if (actionLockCounter == 120) {
            SecureRandom r = new SecureRandom();
            int i = r.nextInt(100) + 1; // 1 to 100

            if ( i <= 25) {
                direction = "up";
            }

            if ( i > 25 && i <= 50) {
                direction = "down";
            }

            if ( i > 50 && i <= 75) {
                direction = "left";
            }

            if ( i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }*/
    }
}
