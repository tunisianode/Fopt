package pada.da.rmi.counter;

import java.rmi.Naming;

public class Server1 {
    public static void main(String args[]) {
        try {
            CounterImpl myCounter = new CounterImpl();
            Naming.rebind("Counter", myCounter);
            System.out.println("Z�hler-Server ist gestartet");
        } catch (Exception e) {
            System.out.println("Ausnahme: " + e.getMessage());
            e.printStackTrace();
        }
    }
}