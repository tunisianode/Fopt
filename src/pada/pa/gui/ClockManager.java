package pada.pa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Clock
{
    private JLabel label;
    private long startTime;

    public Clock(JLabel label)
    {
        this.label = label;
        reset();
    }

    public void update()
    {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long seconds = elapsedTime / 1000;
        long milliSecs = elapsedTime % 1000;
        String prefix;
        if(milliSecs < 10)
        {
            prefix = "00";
        }
        else if(milliSecs < 100)
        {
            prefix = "0";
        }
        else
        {
            prefix = "";
        }
        label.setText(seconds + ":" + prefix + milliSecs);
    }

    public void reset()
    {
        startTime = System.currentTimeMillis();
        update();
    }
}

class UpdateRequest implements Runnable
{
    private Clock clock;

    public UpdateRequest(Clock clock)
    {
        this.clock = clock;
    }

    public void run()
    {
        clock.update();
    }
}

class Ticker extends Thread
{
    private final static long UPDATE_INTERVAL = 10; // Milliseconds
    private UpdateRequest updateReq;

    public Ticker(Clock clock)
    {
        updateReq = new UpdateRequest(clock);
        start();
    }

    public void run()
    {
        try
        {
            while(!isInterrupted())
            {
                EventQueue.invokeLater(updateReq);
                Thread.sleep(UPDATE_INTERVAL);
            }
        }
        catch(InterruptedException e)
        {
        }
    }
}

class EventHandler implements ActionListener
{
    private Clock clock;
    private Ticker ticker;

    public EventHandler(Clock clock)
    {
        this.clock = clock;
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if(s.equals("Start"))
        {
            if(ticker == null)
            {
                clock.reset();
                ticker = new Ticker(clock);
            }
        }
        else if(s.equals("Stopp"))
        {
            if(ticker != null)
            {
                ticker.interrupt();
                ticker = null;
            }
        }
        else if(s.equals("Null"))
        {
            clock.reset();
        }
        else if(s.equals("Ende"))
        {
            System.exit(0);
        }
    }
}

public class ClockManager
{
    public static void main(String[] args)
    {
        JFrame f = new JFrame("Uhr");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(new GridLayout(0, 1));
        JLabel label = new JLabel("", SwingConstants.RIGHT);
        f.add(label);
        JButton b1 = new JButton("Start");
        f.add(b1);
        JButton b2 = new JButton("Stopp");
        f.add(b2);
        JButton b3 = new JButton("Null");
        f.add(b3);
        JButton b4 = new JButton("Ende");
        f.add(b4);
        Clock clock = new Clock(label);
        EventHandler h = new EventHandler(clock);
        b1.addActionListener(h);
        b2.addActionListener(h);
        b3.addActionListener(h);
        b4.addActionListener(h);
        f.setLocation(300, 50);
        f.setSize(150, 200);
        f.setVisible(true);
    }
}