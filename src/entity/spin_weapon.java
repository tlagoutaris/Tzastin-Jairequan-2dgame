package entity;

import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class spin_weapon extends Entity {
    public static int height;
    public double angle = 0;
    public BufferedImage image;
    public int damage = 1;

    public spin_weapon(GamePanel gp) {
        super(gp);


        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 36;
        solidArea.height = 36;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        image = setup("/weapons/spin_weapon1");
    }

    @Override
    public void draw(Graphics2D g2) {

        //rotate weapon image and rotate weapon around player
        Graphics2D g2d = (Graphics2D) g2.create();

        int centerX = gp.player.worldX + gp.tileSize /2;
        int centerY = gp.player.worldY + gp.tileSize /2;
        double distance = gp.tileSize *3;
        int drawX = (int) (gp.player.screenX - distance * Math.cos(angle) - gp.tileSize);
        int drawY = (int) (gp.player.screenY - distance * Math.sin(angle) - gp.tileSize);

        g2d.rotate(angle, gp.player.screenX, gp.player.screenY);
        angle += 0.02;

        g2d.drawImage(image, drawX, drawY, gp.tileSize, gp.tileSize, null);
        g2d.dispose();

        worldX = (int) (centerX + distance * Math.cos(angle) - gp.tileSize);
        worldY = (int) (centerY + distance * Math.sin(angle) - gp.tileSize);

        solidArea.x = worldX + (gp.tileSize  - solidArea.width) /2;
        solidArea.y = worldY + (gp.tileSize  - solidArea.height) /2 ;
        //System.out.println(attackArea.x + " " + attackArea.y);

    }

    @Override
    public void update(){

    }


}