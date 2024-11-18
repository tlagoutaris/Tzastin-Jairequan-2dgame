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
        gp.obj[0].worldY =  10 * gp.tileSize;

        gp.obj[1] = new OBJ_Gem(gp);
        gp.obj[1].worldX =  30 * gp.tileSize;
        gp.obj[1].worldY =  10 * gp.tileSize;
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

        for (int i = 0; i < spawning; i++) {
            x = r.nextInt(0, gp.maxWorldCol - 1) * gp.tileSize;
            y = r.nextInt(0, gp.maxWorldRow - 1) * gp.tileSize;

            setMonster(i, x, y);
        }
    }
}
