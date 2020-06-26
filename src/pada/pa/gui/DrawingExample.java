package pada.pa.gui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class DrawingExample extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawLine(30, 200, 200, 200);
        g.drawLine(200, 200, 200, 100);
        g.drawLine(200, 100, 150, 50);
        g.drawLine(150, 50, 100, 100);
        g.drawLine(100, 100, 100, 200);
        g.drawLine(100, 200, 200, 100);
        g.drawLine(200, 100, 100, 100);
        g.drawLine(100, 100, 200, 200);
        g.drawLine(200, 200, 270, 200);
    }

    public static void main(String argv[]) {
        JFrame f = new JFrame("Nikolaus-Haus");
        DrawingExample housePanel = new DrawingExample();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.add(housePanel);
        f.setLocation(200, 200);
        f.setSize(300, 300);
        f.setVisible(true);
    }
}