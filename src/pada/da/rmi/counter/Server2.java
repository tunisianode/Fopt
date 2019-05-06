package pada.da.rmi.counter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server2
{
    public static void main(String args[])
    {
        try
        {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Counter", new CounterImpl());
            System.out.println("Z�hler-Server ist gestartet");
        }
        catch(Exception e)
        {
            System.out.println("Ausnahme: " + e.getMessage());
            e.printStackTrace();
        }
    }
}