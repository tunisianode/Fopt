package pada.pa.gui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MixedLayoutExample extends JFrame
{
    public MixedLayoutExample()
    {
        setTitle("Beispiel f�r ein gemischtes Layout");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //Zentrum:
        JLabel picture = new JLabel("Bild oder Grafik", JLabel.CENTER); 
        add(picture, BorderLayout.CENTER);
        
        //Buttons im S�den mit FlowLayout:
        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(new JButton("Aaaaa"));
        southPanel.add(new JButton("Bbbbbbbbbbbbbb"));
        southPanel.add(new JButton("Cc"));
        add(southPanel, BorderLayout.SOUTH);
        
        //Buttons im Osten mit einspaltigem GridLayout:
        JPanel eastPanel = new JPanel(new GridLayout(0, 1));
        eastPanel.add(new JButton("11"));
        eastPanel.add(new JButton("2222222222"));
        eastPanel.add(new JButton("3333"));
        eastPanel.add(new JButton("4"));
        add(eastPanel, BorderLayout.EAST);

        setLocation(100, 100);
        setSize(700, 200);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new MixedLayoutExample();
    }
}
