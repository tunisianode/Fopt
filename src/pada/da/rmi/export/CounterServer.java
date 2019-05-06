package pada.da.rmi.export;

import java.rmi.Naming;

public class CounterServer
{
    public static void main(String args[])
    {
        try
        {
            Counter c = new CounterImpl();
            Naming.rebind("Counter", c);
        }
        catch(Exception e)
        {
            System.out.println("Ausnahme: " + e.getMessage());
        }
    }
}