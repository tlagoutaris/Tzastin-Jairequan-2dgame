package main;

import monster.MON_Skull;
import object.OBJ_Gem;
import java.security.SecureRandom;

public class AssetSetter {

    GamePanel gp;
    int currentIndex = 0;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void update() {

        // do something

    }

    //objects
    public void setObject(){
        gp.obj[0] = new OBJ_Gem(gp);
        gp.obj[0].worldX =  20 * gp.tileSize;
        gp.obj[0].worldY =  9 * gp.tileSize;

        gp.obj[1] = new OBJ_Gem(gp);
        gp.obj[1].worldX =  30 * gp.tileSize;
        gp.obj[1].worldY =  3 * gp.tileSize;
    }

    //enemies
    public void setMonster(int index, int x, int y) {
        gp.enemy[index] = new MON_Skull(gp);
        gp.enemy[index].worldX = x; // gp.tileSize * 30
        gp.enemy[index].worldY = y; // gp.tileSize * 5

        currentIndex++;
    }

    public void spawnGroup(int spawning) {
        SecureRandom r = new SecureRandom();
        int x;
        int y;

        for (int i = currentIndex; i < currentIndex + spawning; i++) {
            x = r.nextInt(0, gp.maxWorldCol) * gp.tileSize;
            y = r.nextInt(0, gp.maxWorldRow) * gp.tileSize;

            setMonster(i, x, y);
        }

        currentIndex += spawning;
    }

    public void spawnGroupTest(int spawning) {
        SecureRandom r = new SecureRandom();
        int x;
        int y;
        int horizontal; //0 for left of player, 1 for right
        int vertical;   //0 for above player, 1 for below

        for (int i = 0; i < spawning; i++) {
            horizontal = r.nextInt(0, 2);
            vertical = r.nextInt(0, 2);


            //makes monsters spawn randomly in the area anywhere besides right next to player
            if (horizontal == 0){
                x = r.nextInt(gp.player.worldX - (5* gp.tileSize), gp.player.worldX - (3 * gp.tileSize));
            }else{
                x = r.nextInt(gp.player.worldX + (3 * gp.tileSize), (gp.player.worldX + (5* gp.tileSize)));
            }

            if (vertical == 0){
                y = r.nextInt(gp.player.worldY - (5 * gp.tileSize), gp.player.worldY - (3 * gp.tileSize));
            }else{
                y = r.nextInt(gp.player.worldY + (3 * gp.tileSize), gp.player.worldY + (5 * gp.tileSize));
            }

            setMonster(i, x, y);
        }
    }
}
