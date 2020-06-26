package pada.da.rmi.counter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class CounterImpl extends UnicastRemoteObject
        implements Counter {
    private int counter;

    public CounterImpl() throws RemoteException {
        /*
         * super(); counter = 0;
         */
    }

    public synchronized int reset() throws RemoteException {
        System.out.println("Methode reset wurde aufgerufen");
        counter = 0;
        return counter;
    }

    public synchronized int increment() throws RemoteException {
        counter++;
        return counter;
    }
}