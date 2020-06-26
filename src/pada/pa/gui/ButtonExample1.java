package pada.pa.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ButtonExample1 extends JFrame {
    public ButtonExample1() {
        super("Beispiel f�r Button");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton b = new JButton("Dr�ck mich!");
        add(b);
        MyHandler h = new MyHandler();
        b.addActionListener(h);
        setLocation(100, 50);
        setSize(270, 90);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ButtonExample1();
    }
}

class MyHandler implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Der Button wurde gedr�ckt.");
    }
}