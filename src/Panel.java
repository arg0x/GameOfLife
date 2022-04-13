import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Panel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    int xPanel = 1920;
    int yPanel = 1080;
    int sqSize = 15;

    int xWidth = xPanel / sqSize;
    int yHeight = yPanel / sqSize;

    int[][] life = new int[xWidth][yHeight];
    int[][] afterLife = new int[xWidth][yHeight];

    boolean startProgram = true;
    int initial = -1;

    Timer timer;

    public Panel() {
        setSize(xPanel, yPanel);
        setLayout(null);
        setBackground(Color.black);
        setFocusable(true);

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        timer = new Timer(80, this);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        grid(graphics);
        display(graphics);
    }

    private void grid(Graphics graphics) {
        for (int i = 0; i < life.length; i++) {
            graphics.drawLine(0, i * sqSize, xPanel, i * sqSize);
            graphics.drawLine(i * sqSize, 0, i * sqSize, yPanel);
        }
    }

    private void spawn() {
        for (int x = 0; x < life.length; x++) {
            for (int y = 0; y < (yHeight); y++) {
                if ((int) (Math.random() * 5) == 0) {
                    afterLife[x][y] = 1;
                }
            }
        }

    }

    private void display(Graphics graphics) {
        graphics.setColor(Color.green);
        copyArray();

        for (int x = 0; x < life.length; x++) {
            for (int y = 0; y < (yHeight); y++) {
                if (life[x][y] == 1) {
                    graphics.fillRect(x * sqSize, y * sqSize, sqSize, sqSize);
                }
            }
        }

    }

    private void copyArray() {
        for (int x = 0; x < life.length; x++) {
            for (int y = 0; y < (yHeight); y++) {
                life[x][y] = afterLife[x][y];
            }
        }

    }

    private int check(int x, int y) {
        int alive = 0;

        alive += life[(x + xWidth - 1) % xWidth][(y + yHeight - 1) % yHeight];
        alive += life[(x + xWidth) % xWidth][(y + yHeight - 1) % yHeight];

        alive += life[(x + xWidth + 1) % xWidth][(y + yHeight - 1) % yHeight];
        alive += life[(x + xWidth - 1) % xWidth][(y + yHeight) % yHeight];

        alive += life[(x + xWidth + 1) % xWidth][(y + yHeight) % yHeight];
        alive += life[(x + xWidth - 1) % xWidth][(y + yHeight + 1) % yHeight];

        alive += life[(x + xWidth) % xWidth][(y + yHeight + 1) % yHeight];
        alive += life[(x + xWidth + 1) % xWidth][(y + yHeight + 1) % yHeight];

        return alive;

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        int alive;

        for (int x = 0; x < life.length; x++) {
            for (int y = 0; y < (yHeight); y++) {
                alive = check(x, y);
                if (alive == 3) {
                    afterLife[x][y] = 1;
                } else if (alive == 2 && life[x][y] == 1) {
                    afterLife[x][y] = 1;
                } else {
                    afterLife[x][y] = 0;
                }
            }
        }
        repaint();
    }

    private void clear() {
        for (int x = 0; x < life.length; x++) {
            for (int y = 0; y < (yHeight); y++) {
                afterLife[x][y] = 0;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        int x = event.getX() / sqSize;
        int y = event.getY() / sqSize;

        if (life[x][y] == 0 && initial == 0) {
            afterLife[x][y] = 1;
        } else if (life[x][y] == 1 && initial == 1) {
            afterLife[x][y] = 0;
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent event) {

    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        int x = event.getX() / sqSize;
        int y = event.getY() / sqSize;

        if (life[x][y] == 0) {
            initial = 0;
        } else {
            initial = 1;
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent event) {

        initial = -1;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_Z) {
            spawn();
        } else if (key == KeyEvent.VK_C) {
            clear();
            timer.stop();
        } else if (key == KeyEvent.VK_X) {
            timer.start();
        } else if (key == KeyEvent.VK_S) {
            timer.stop();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
}
