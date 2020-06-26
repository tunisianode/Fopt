package pada.pa.gui;

import javax.swing.*;

@SuppressWarnings("serial")
class MyFrame extends JFrame {
    public MyFrame(String title, String labelText) {
        super(title);
        add(new JLabel(labelText));
        setLocation(300, 50);
        setSize(400, 100);
        setVisible(true);
    }
}

public class LabelExample2 {
    public static void main(String[] args) {
        new MyFrame("Dies ist ein Beispiel f�r Label", "Hallo Welt");
    }
}