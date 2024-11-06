package main;

import monster.MON_Skull;
import object.OBJ_Gem;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
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
    public void setMonster() {
        gp.enemy[0] = new MON_Skull(gp);
        gp.enemy[0].worldX = gp.tileSize * 30;
        gp.enemy[0].worldY = gp.tileSize * 5;
    }
}
