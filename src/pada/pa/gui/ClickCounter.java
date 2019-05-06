package pada.pa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial") 
class ClickCounterPanel extends JPanel implements ActionListener
{
    private int times;
    private JLabel l;

    public ClickCounterPanel()
    {
        setLayout(new GridLayout(0, 1));
        JButton b = new JButton("Dr�ck mich!");
        add(b);
        b.addActionListener(this);
        l = new JLabel();
        add(l);
    }

    public void actionPerformed(ActionEvent evt)
    {
        times++;
        l.setText("Ich wurde " + times + " mal gedr�ckt.");
    }
}

@SuppressWarnings("serial")
public class ClickCounter extends JApplet
{
    public void init()
    {
        add(new ClickCounterPanel());
    }

    public static void main(String[] args)
    {
        JFrame f = new JFrame("Beispiel f�r Button");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.add(new ClickCounterPanel());
        f.setLocation(100, 50);
        f.setSize(300, 100);
        f.setVisible(true);
    }
}