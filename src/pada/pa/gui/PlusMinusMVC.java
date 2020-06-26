package pada.pa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

interface PlusMinusListener {
    public void valueChanged(PlusMinusModel model);
}

class PlusMinusModel {
    private int counter;
    private int min, max;
    private ArrayList<PlusMinusListener> listeners;

    public PlusMinusModel(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min > max");
        }
        this.min = min;
        this.max = max;
        this.counter = min;
        this.listeners = new ArrayList<PlusMinusListener>();
    }

    public void increment() {
        if (counter < max) {
            counter++;
            fireModelChanged();
        }
    }

    public void decrement() {
        if (counter > min) {
            counter--;
            fireModelChanged();
        }
    }

    public int getCounter() {
        return counter;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void addPlusMinusListener(PlusMinusListener l) {
        listeners.add(l);
    }

    public void removePlusMinusListener(PlusMinusListener l) {
        listeners.remove(l);
    }

    private void fireModelChanged() {
        for (PlusMinusListener l : listeners) {
            l.valueChanged(this);
        }
    }
}

@SuppressWarnings("serial")
class CounterView extends JLabel implements PlusMinusListener {
    public static final int DECIMAL_FORMAT = 1;
    public static final int HEXADECIMAL_FORMAT = 2;
    public static final int OCTAL_FORMAT = 3;
    private int format;

    public CounterView(PlusMinusModel model, int format) {
        this.format = format;
        this.valueChanged(model);
    }

    public CounterView(PlusMinusModel model) {
        this(model, DECIMAL_FORMAT);
    }

    public void valueChanged(PlusMinusModel model) {
        String s = "unbekanntes Format";
        switch (format) {
            case DECIMAL_FORMAT:
                s = model.getCounter() + " (dezimal)";
                break;
            case HEXADECIMAL_FORMAT:
            case OCTAL_FORMAT:
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                String formatString,
                        appendix;
                if (format == HEXADECIMAL_FORMAT) {
                    formatString = "%x";
                    appendix = " (hexadezimal)";
                } else {
                    formatString = "%o";
                    appendix = " (oktal)";
                }
                pw.printf(formatString, model.getCounter());
                s = sw.toString() + appendix;
        }
        setText(s);
    }
}

@SuppressWarnings("serial")
class ButtonView extends JButton implements PlusMinusListener {
    public static final int MAX_LIMIT = 1;
    public static final int MIN_LIMIT = 2;
    private int limitType;

    public ButtonView(String text, PlusMinusModel model, int limitType) {
        super(text);
        this.limitType = limitType;
        this.valueChanged(model);
    }

    public void valueChanged(PlusMinusModel model) {
        int limit;
        if (limitType == MAX_LIMIT) {
            limit = model.getMax();
        } else {
            limit = model.getMin();
        }
        if (model.getCounter() == limit) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }
}

class PlusController implements ActionListener {
    private PlusMinusModel model;

    public PlusController(PlusMinusModel model) {
        this.model = model;
    }

    public void actionPerformed(ActionEvent e) {
        model.increment();
    }
}

class MinusController implements ActionListener {
    private PlusMinusModel model;

    public MinusController(PlusMinusModel model) {
        this.model = model;
    }

    public void actionPerformed(ActionEvent e) {
        model.decrement();
    }
}

@SuppressWarnings("serial")
public class PlusMinusMVC extends JFrame {
    public PlusMinusMVC(PlusMinusModel model, String title) {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(0, 1));
        CounterView view1 = new CounterView(model, CounterView.DECIMAL_FORMAT);
        model.addPlusMinusListener(view1);
        CounterView view2 = new CounterView(model,
                CounterView.HEXADECIMAL_FORMAT);
        model.addPlusMinusListener(view2);
        CounterView view3 = new CounterView(model, CounterView.OCTAL_FORMAT);
        model.addPlusMinusListener(view3);
        ButtonView plus = new ButtonView("+", model, ButtonView.MAX_LIMIT);
        model.addPlusMinusListener(plus);
        ButtonView minus = new ButtonView("-", model, ButtonView.MIN_LIMIT);
        model.addPlusMinusListener(minus);
        c.add(plus);
        c.add(view1);
        c.add(view2);
        c.add(view3);
        c.add(minus);
        add(c);
        PlusController pc = new PlusController(model);
        MinusController mc = new MinusController(model);
        plus.addActionListener(pc);
        minus.addActionListener(mc);
        setSize(300, 150);
        setLocation(50, 50);
        setVisible(true);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Parameter: min max");
            System.exit(0);
        }
        int min = 0, max = 0;
        try {
            min = Integer.parseInt(args[0]);
            max = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Zahlenformatfehler");
            System.exit(0);
        }
        PlusMinusModel model = new PlusMinusModel(min, max);
        new PlusMinusMVC(model, "PlusMinus");
        new PlusMinusMVC(model, "PlusMinus 2");
    }
}