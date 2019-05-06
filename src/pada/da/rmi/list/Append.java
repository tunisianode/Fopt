package pada.da.rmi.list;

public interface Append extends java.rmi.Remote
{
    public List append(List l) throws java.rmi.RemoteException;
}