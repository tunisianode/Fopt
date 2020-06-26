package pada.pa.gui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
class AnimationPanel extends JPanel {
    private int x;
    private int y = 10;
    boolean right = true;

    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(x, y, 50, 20);
        g.drawOval(10, 50, 300, 300);
        g.fillArc(10, 50, 300, 300, 0, x);
    }

    public synchronized void next() {
        if (right) {
            x++;
            if (x == 360)
                right = false;
        } else {
            x--;
            if (x == 0)
                right = true;
        }
        repaint();
    }
}

class Animator extends Thread {
    private AnimationPanel animPanel;

    public Animator(AnimationPanel animPanel) {
        this.animPanel = animPanel;
    }

    public void run() {
        while (true) {
            animPanel.next();
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
            }
        }
    }
}

public class Animation {
    public static void main(String argv[]) {
        JFrame f = new JFrame("Animation");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        AnimationPanel a = new AnimationPanel();
        f.add(a);
        f.setLocation(100, 100);
        f.setSize(450, 400);
        f.setVisible(true);
        Animator animThread = new Animator(a);
        animThread.start();
    }
}