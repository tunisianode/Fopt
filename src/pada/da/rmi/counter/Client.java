package pada.da.rmi.counter;

import java.rmi.Naming;

public class Client
{
    public static void main(String args[])
    {
        if(args.length != 2)
        {
            System.out.println("Notwendige Kommandozeilenargumente: "
                               + "<Name des Server-Rechners> <Anzahl>");
            return;
        }
        try
        {
            Counter myCounter = (Counter) Naming.lookup("rmi://" + args[0]
                                                        + "/Counter");
            // set counter to initial value of 0
            System.out.println("Z�hler wird auf 0 gesetzt.");
            myCounter.reset();
            System.out.println("Nun wird der Z�hler erh�ht.");
            // calculate start time
            int count = new Integer(args[1]).intValue();
            long startTime = System.currentTimeMillis();
            // increment count times
            int result = 0;
            for(int i = 0; i < count; i++)
            {
                result = myCounter.increment();
            }
            // calculate stop time; print out statistics
            long stopTime = System.currentTimeMillis();
            long duration = stopTime - startTime;
            System.out.println("Gesamtzeit = " + duration + " msecs");
            if(count > 0)
            {
                System.out.println("Durchschnittszeit = "
                                   + ((duration) / (float) count) + " msecs");
            }
            System.out.println("Letzter Z�hlerstand: " + result);
        }
        catch(Exception e)
        {
            System.out.println("Ausnahme: " + e);
        }
    }
}