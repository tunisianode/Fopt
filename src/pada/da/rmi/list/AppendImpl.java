package pada.da.rmi.list;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class AppendImpl extends UnicastRemoteObject implements Append {
    public AppendImpl() throws RemoteException {
    }

    public List append(List l) throws RemoteException {
        System.out.print("erhaltene Liste: ");
        l.print();
        l.append(4711);
        System.out.print("manipulierte Liste: ");
        l.print();
        return l;
    }
}