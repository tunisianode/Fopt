package pada.pa.gui;

import javax.swing.*;

public class LabelExample1
{
    public static void main(String[] args)
    {
        JFrame f = new JFrame("Beispiel für Label");
        f.add(new JLabel("Hallo Welt"));
        f.setLocation(300, 50);
        f.setSize(400, 100); // f.pack();
        f.setVisible(true);
    }
}