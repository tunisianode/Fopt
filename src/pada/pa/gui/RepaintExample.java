package pada.pa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial") 
class CirclePanel extends JPanel
{
    private Color color;

    public void setColor(Color c)
    {
        color = c;
        // die folgende Anweisung muss einkommentiert werden,
        // damit das Programm korrekt arbeitet:
        //repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(color);
        int diameter = Math.min(getWidth(), getHeight()) - 6;
        g.fillOval(3, 3, diameter, diameter);
    }
}

@SuppressWarnings("serial")
public class RepaintExample extends JFrame implements ActionListener
{
    private CirclePanel circlePanel;

    public RepaintExample(String title)
    {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel total = new JPanel(new BorderLayout());
        circlePanel = new CirclePanel();
        circlePanel.setColor(Color.RED);
        total.add(circlePanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
        JButton red = new JButton("Rot");
        red.setBackground(Color.RED);
        red.addActionListener(this);
        buttonPanel.add(red);
        JButton green = new JButton("Gr�n");
        green.setBackground(Color.GREEN);
        green.addActionListener(this);
        buttonPanel.add(green);
        total.add(buttonPanel, BorderLayout.SOUTH);
        add(total);
        setLocation(200, 200);
        setSize(300, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if(command.equals("Rot"))
        {
            circlePanel.setColor(Color.RED);
        }
        else
        // if(e.equals("Gr�n"))
        {
            circlePanel.setColor(Color.GREEN);
        }
    }

    public static void main(String argv[])
    {
        new RepaintExample("Farbenwahl");
    }
}