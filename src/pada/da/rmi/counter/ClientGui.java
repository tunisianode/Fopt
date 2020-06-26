package pada.da.rmi.counter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

@SuppressWarnings("serial")
public class ClientGui extends JFrame implements ActionListener {
    private JLabel label;
    private Counter counter;

    public ClientGui(String title, Counter counter) {
        super(title);
        this.counter = counter;
        setLayout(new GridLayout(0, 1));
        label = new JLabel("", SwingConstants.CENTER);
        JButton incrementButton = new JButton("Erh�hen");
        JButton resetButton = new JButton("Zur�cksetzen");
        add(label);
        add(incrementButton);
        add(resetButton);
        incrementButton.addActionListener(this);
        resetButton.addActionListener(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocation(50, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();
        if (command.equals("Erh�hen")) {
            increment();
        } else if (command.equals("Zur�cksetzen")) {
            reset();
        }
    }

    private void reset() {
        RMICaller caller = new RMICaller(counter, false, label);
        caller.start();
    }

    private void increment() {
        RMICaller caller = new RMICaller(counter, true, label);
        caller.start();
    }

    /**
     * main-Methode.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Notwendige Argumente: " + "<Name des Servers> "
                    + "<Name des Objekts>");
            return;
        }
        try {
            Counter counter = (Counter) Naming.lookup("rmi://" + args[0] + "/"
                    + args[1]);
            new ClientGui("Z�hler-Client", counter);
        } catch (Exception e) {
            System.out.println("Ausnahme bei der Initialisierung: "
                    + e.getMessage());
            System.exit(0);
        }
    }
}

class RMICaller extends Thread {
    private Counter counter;
    private boolean increment; // true: call increment, false: call
    // reset
    private JLabel label;

    public RMICaller(Counter counter, boolean increment, JLabel label) {
        this.counter = counter;
        this.increment = increment;
        this.label = label;
    }

    public void run() {
        String message;
        try {
            int newValue;
            if (increment) {
                newValue = counter.increment();
            } else {
                newValue = counter.reset();
            }
            message = "" + newValue;
        } catch (RemoteException e) {
            message = e.getMessage();
        }
        LabelUpdate update = new LabelUpdate(label, message);
        SwingUtilities.invokeLater(update);
    }
}

class LabelUpdate implements Runnable {
    private JLabel label;
    private String text;

    public LabelUpdate(JLabel label, String text) {
        this.label = label;
        this.text = text;
    }

    public void run() {
        label.setText(text);
    }
}