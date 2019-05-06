package pada.pa.gui;

import pada.pa.basic.GroupTree;

import javax.swing.*;

public class SwingThreads
{
    public static void main(String[] args)
    {
        System.out.println("Alle Threads beim Start von main:");
        GroupTree.dumpAll();
        System.out.println("-----------------------");
        JFrame f1 = new JFrame("Test");
        f1.setSize(100, 100);
        f1.setLocation(50, 50);
        System.out.println("Alle Threads, nachdem ein JFrame "
                           + "erzeugt wurde:");
        GroupTree.dumpAll();
        System.out.println("-----------------------");
        f1.setVisible(true);
        System.out.println("Alle Threads, nachdem ein JFrame "
                           + "sichtbar gemacht wurde:");
        GroupTree.dumpAll();
        System.out.println("-----------------------");
        JFrame f2 = new JFrame("Test2");
        f2.setSize(100, 100);
        f2.setLocation(150, 150);
        f2.setVisible(true);
        System.out.println("Alle Threads, nachdem ein zweites JFrame "
                           + "erzeugt und sichtbar gemacht wurde:");
        GroupTree.dumpAll();
        System.out.println("-----------------------");
        System.exit(0);
    }
}