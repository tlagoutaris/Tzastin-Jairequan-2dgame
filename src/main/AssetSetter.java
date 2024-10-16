package main;

import monster.MON_Skull;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setMonster() {
        gp.enemy[0] = new MON_Skull(gp);
        gp.enemy[0].worldX = gp.tileSize * 10;
        gp.enemy[0].worldY = gp.tileSize * 10;
    }
}
