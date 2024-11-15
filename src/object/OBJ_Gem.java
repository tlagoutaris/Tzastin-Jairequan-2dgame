package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Gem extends SuperObject{
    GamePanel gp;
    public static int height;
    public OBJ_Gem(GamePanel gp) {
        name = "Gem";
        height = 14;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/gem.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
