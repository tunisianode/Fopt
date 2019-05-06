package pada.da.rmi.sleep;

public interface Sleep extends java.rmi.Remote
{
    void sleep(int secs) throws java.rmi.RemoteException;
}