package pada.pa.gui;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ButtonExample2a extends ButtonExample2 {
    public void actionPerformed(ActionEvent evt) {
        Thread t = Thread.currentThread();
        System.out.println("Die Methode wird von " + t.getName()
                + " ausgef�hrt.");
    }

    public static void main(String[] args) {
        new ButtonExample2a();
    }
}