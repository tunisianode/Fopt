package pada.pa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

class NumberAndSleepTime {
    private int number;
    private long sleepTime;

    public NumberAndSleepTime(int number, long sleepTime) {
        this.number = number;
        this.sleepTime = sleepTime;
    }

    public int getNumber() {
        return number;
    }

    public long getSleepTime() {
        return sleepTime;
    }
}

class MySwingWorker extends SwingWorker<long[], NumberAndSleepTime> {
    private long maxSleepTime;
    private int runs;
    private JLabel numberLabel, timeLabel;
    private JButton b;

    public MySwingWorker(long maxSleepTime, int runs,
                         JLabel numberLabel, JLabel timeLabel,
                         JButton b) {
        this.maxSleepTime = maxSleepTime;
        this.runs = runs;
        this.numberLabel = numberLabel;
        this.timeLabel = timeLabel;
        this.b = b;
    }

    protected long[] doInBackground() throws Exception {
        SwingWorkerDemo.sysout("doInBackground called by " +
                Thread.currentThread().getName());
        long[] result = new long[runs];
        for (int number = 1; number <= runs; number++) {
            long time = (long) (Math.random() * maxSleepTime);
            publish(new NumberAndSleepTime(number, time));
            double progress = (double) (number * 100) / (double) runs;
            setProgress((int) progress);
            result[number - 1] = time;
            Thread.sleep(time);
        }
        return result;
    }

    protected void process(List<NumberAndSleepTime> list) {
        SwingWorkerDemo.sysout("process called by " +
                Thread.currentThread().getName());
        NumberAndSleepTime nast = list.get(list.size() - 1);
        numberLabel.setText("" + nast.getNumber());
        timeLabel.setText("" + nast.getSleepTime());
    }

    protected void done() {
        SwingWorkerDemo.sysout("done called by " +
                Thread.currentThread().getName());
        try {
            long[] result = get();
            String text = "";
            for (long l : result) {
                text += l + " ";
            }
            timeLabel.setText(text);
        } catch (Exception e) {
        }
        b.setEnabled(true);
    }
}

class MyPropsListener implements PropertyChangeListener {
    private JProgressBar progressBar;
    private JLabel statusLabel, progressLabel;

    public MyPropsListener(JProgressBar progressBar,
                           JLabel statusLabel,
                           JLabel progressLabel) {
        this.progressBar = progressBar;
        this.statusLabel = statusLabel;
        this.progressLabel = progressLabel;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        SwingWorkerDemo.sysout("propertyChange called by " +
                Thread.currentThread().getName());
        JLabel l = null;
        if (evt.getPropertyName().equals("state")) {
            l = statusLabel;
        } else if (evt.getPropertyName().equals("progress")) {
            l = progressLabel;
            progressBar.setValue((Integer) (evt.getNewValue()));
        }
        String message = evt.getPropertyName() +
                ": " + evt.getOldValue() +
                " => " + evt.getNewValue();
        if (l != null) {
            l.setText(message);
        } else {
            System.out.println(message);
        }
    }
}

public class SwingWorkerDemo extends JFrame implements ActionListener {
    private static final int RUNS = 10;
    private static final long MAX_SLEEP_TIME = 8000;

    private JProgressBar progressBar;
    private JLabel statusLabel, progressLabel;
    private JLabel numberLabel, timeLabel;
    private JButton b;

    public SwingWorkerDemo() {
        super("Swing Worker Demo");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 1));
        progressBar = new JProgressBar(0, 100);
        add(progressBar);
        statusLabel = new JLabel();
        add(statusLabel);
        progressLabel = new JLabel();
        add(progressLabel);
        numberLabel = new JLabel();
        add(numberLabel);
        timeLabel = new JLabel();
        add(timeLabel);
        b = new JButton("Execute");
        b.addActionListener(this);
        add(b);

        setLocation(30, 30);
        setSize(380, 250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        progressBar.setValue(0);
        statusLabel.setText("");
        progressLabel.setText("");
        numberLabel.setText("");
        timeLabel.setText("");
        b.setEnabled(false);
        MySwingWorker worker = new MySwingWorker(MAX_SLEEP_TIME, RUNS,
                numberLabel, timeLabel,
                b);
        worker.addPropertyChangeListener(new MyPropsListener(progressBar,
                statusLabel,
                progressLabel));
        worker.execute();
    }

    public static void sysout(String message) {
        if (SwingUtilities.isEventDispatchThread()) {
            message += " (is EDT)";
        } else {
            message += " (is not EDT)";
        }
        System.out.println(message);
    }

    public static void main(String[] args) {
        new SwingWorkerDemo();
    }
}
