package pada.da.rmi.list;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("N�tige Kommandozeilenargumente: "
                    + "<Name der Servers> "
                    + "<Zahl 1> <Zahl 2> ... <Zahl N>");
            return;
        }
        try {
            Append server = (Append) Naming.lookup("rmi://" + args[0]
                    + "/AppendServer");
            System.out.println("Kontakt zu Server hergestellt");
            List l = new List();
            for (int i = 1; i < args.length; i++) {
                int value = Integer.parseInt(args[i]);
                l.append(value);
            }
            List lReturned = server.append(l);
            System.out.print("Nach dem RMI-Aufruf noch vorhandene Liste,"
                    + " die als Parameter �bergeben wurde: ");
            l.print();
            System.out.print("Vom RMI-Aufruf zur�ckgelieferte Liste: ");
            lReturned.print();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}