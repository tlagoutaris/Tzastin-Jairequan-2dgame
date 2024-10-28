package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Gem extends SuperObject{
    public OBJ_Gem(){
        name = "Gem";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/gem.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
