package pada.pa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ButtonExample3 extends JFrame implements ActionListener {
    private int times;
    private JLabel l;

    public ButtonExample3() {
        super("Beispiel f�r Button");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 1));
        JButton b = new JButton("Dr�ck mich!");
        add(b);
        b.addActionListener(this);
        l = new JLabel();
        add(l);
        setLocation(100, 50);
        setSize(300, 120);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        times++;
        l.setText("Der Button wurde " + times + " mal gedr�ckt.");
    }

    public static void main(String[] args) {
        new ButtonExample3();
    }
}