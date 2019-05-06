package pada.da.rmi.sleep;

import java.rmi.Naming;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            SleepImpl server;
            server = new SleepImpl();
            Naming.rebind("SleepServer1", server);
            server = new SleepImpl();
            Naming.rebind("SleepServer2", server);
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}