import javax.swing.JFrame;

public class Frame extends JFrame {

    public Frame() {
        add(new Panel());

        setTitle("Game of Life");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Frame();
    }
}