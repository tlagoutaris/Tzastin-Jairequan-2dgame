import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class GameWindow {

    JFrame frame = new JFrame("Game");
    JLabel label = new JLabel("");

    int WIDTH = 1920;
    int HEIGHT = 1080;

    GameWindow() {

        // Default Window
        /*
        label.setBounds(0,0,150,50);
        label.setFont(new Font(null, Font.PLAIN, 20));

        frame.add(label);*/

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
