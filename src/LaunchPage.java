import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchPage implements ActionListener{

    JFrame frame = new JFrame();
    JButton startButton = new JButton("Start");
    JButton exitButton = new JButton("Exit");

    public LaunchPage(){

        // Start Button
        startButton.setBounds(100, 160, 200, 40);
        startButton.setFocusable(false);
        startButton.addActionListener(this);

        // Exit Button
        exitButton.setBounds(100, 220, 200, 40);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);

        // add buttons to frame
        frame.add(startButton);
        frame.add(exitButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            GameWindow gameWindow = new GameWindow();
        }

        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
