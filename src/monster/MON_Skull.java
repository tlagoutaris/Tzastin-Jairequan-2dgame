package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;

public class MON_Skull extends Entity {

    //BufferedImage peaceful_idle, peaceful_up_left, peaceful_up_right, peaceful_down_left, peaceful_down_right;
    //BufferedImage alerted_idle, alerted_up_left, alerted_up_right, alerted_down_left, alerted_down_right;

    //String state = "peaceful";


    public MON_Skull(GamePanel gp) {
        super(gp);

        type = 1;
        name = "Skull";
        speed = 1;
        maxLife = 4;
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
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 1 && contactPlayer){
            if(gp.player.invincible == false){
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        //moves to player
        if(!collisionOn){

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
