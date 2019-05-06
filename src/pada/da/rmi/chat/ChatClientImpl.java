package pada.da.rmi.chat;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class ChatClientImpl extends UnicastRemoteObject implements ChatClient
{
    private String name;
    private JTextArea output;

    public ChatClientImpl(String n) throws RemoteException
    {
        name = n;
    }

    public void setOutput(JTextArea ta)
    {
        output = ta;
    }

    public String getName() throws RemoteException
    {
        return name;
    }

    public void print(String msg) throws RemoteException
    {
        if(output != null)
        {
            Updater updater = new Updater(output, "> " + msg + "\n");
            SwingUtilities.invokeLater(updater);
        }
    }
}

class Updater implements Runnable
{
    private JTextArea output;
    private String msg;

    public Updater(JTextArea output, String msg)
    {
        this.output = output;
        this.msg = msg;
    }

    public void run()
    {
        output.append(msg);
    }
}