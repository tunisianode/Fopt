package pada.pa.gui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class BorderLayoutExample extends JFrame {
    public BorderLayoutExample(String[] text) {
        setTitle("Beispiel f�r BorderLayout");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JButton("Norden"), BorderLayout.NORTH);
        add(new JButton("Westen"), BorderLayout.WEST);
        add(new JButton("S�den"), BorderLayout.SOUTH);
        add(new JButton("Osten"), BorderLayout.EAST);
        add(new JButton("Zentrum"), BorderLayout.CENTER);
        setLocation(100, 100);
        setSize(400, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BorderLayoutExample(args);
    }
}