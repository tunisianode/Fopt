package pada.da.rmi.export;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class MigratorImpl extends UnicastRemoteObject implements Migrator
{
    public MigratorImpl() throws RemoteException
    {
    }

    public Counter migrate(Counter counter) throws RemoteException
    {
        try
        {
            UnicastRemoteObject.exportObject(counter, 0);
        }
        catch(RemoteException e)
        {
            System.out.println("Ausnahme: " + e);
        }
        return counter;
    }
}