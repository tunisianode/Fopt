package pada.da.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class ChatServerImpl extends UnicastRemoteObject implements ChatServer
{
    private ArrayList<ChatClient> allClients;

    public ChatServerImpl() throws RemoteException
    {
        allClients = new ArrayList<ChatClient>();
    }

    public synchronized boolean addClient(ChatClient objRef)
                                                            throws RemoteException
    {
        String name = objRef.getName();
        for(Iterator<ChatClient> iter = allClients.iterator(); iter.hasNext();)
        {
            ChatClient cc = iter.next();
            try
            {
                if(cc.getName().equals(name))
                {
                    return false;
                }
            }
            catch(RemoteException exc)
            {
                iter.remove();
            }
        }
        allClients.add(objRef);
        return true;
    }

    public synchronized void removeClient(ChatClient objRef)
                                                            throws RemoteException
    {
        allClients.remove(objRef);
    }

    public synchronized void sendMessage(String name, String msg)
                                                                 throws RemoteException
    {
        for(Iterator<ChatClient> iter = allClients.iterator(); iter.hasNext();)
        {
            ChatClient cc = iter.next();
            try
            {
                cc.print(name + ": " + msg);
            }
            catch(RemoteException exc)
            {
                iter.remove();
            }
        }
    }
}