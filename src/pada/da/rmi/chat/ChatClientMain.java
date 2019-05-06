package pada.da.rmi.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ChatClientMain
{
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("N�tige Kommandozeilenargumente: "
                               + "<Name des Servers> <eigener Name>");
            return;
        }
        try
        {
            ChatServer server = (ChatServer) Naming.lookup("rmi://" + args[0]
                                                           + "/ChatServer");
            System.out.println("Kontakt zu Server hergestellt");
            ChatClientImpl client = new ChatClientImpl(args[1]);
            ChatGUI gui = new ChatGUI(client, server);
            client.setOutput(gui.getOutput());
            if(server.addClient(client))
            {
                SwingUtilities.invokeLater(new SetVisibleRequest(gui));
            }
            else
            {
                System.out.println("Es ist schon jemand unter "
                                   + "diesem Namen angemeldet");
                System.exit(0);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

class SetVisibleRequest implements Runnable
{
    private ChatGUI gui;

    public SetVisibleRequest(ChatGUI gui)
    {
        this.gui = gui;
    }

    public void run()
    {
        gui.setVisible(true);
    }
}

@SuppressWarnings("serial")
class ChatGUI extends JFrame implements ActionListener
{
    private ChatClient client;
    private ChatServer server;
    private JTextField input;
    private JTextArea output;
    private JButton exit;

    public ChatGUI(ChatClient client, ChatServer server)
    {
        try
        {
            setTitle("ChatClient f�r " + client.getName());
        }
        catch(RemoteException e)
        {
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.client = client;
        this.server = server;
        setLayout(new BorderLayout());
        input = new JTextField();
        input.addActionListener(this);
        add(input, BorderLayout.NORTH);
        output = new JTextArea();
        output.setEditable(false);
        JScrollPane sp = new JScrollPane(output);
        add(sp, BorderLayout.CENTER);
        exit = new JButton("Ende");
        exit.addActionListener(this);
        add(exit, BorderLayout.SOUTH);
        setSize(300, 700);
    }

    public JTextArea getOutput()
    {
        return output;
    }

    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getActionCommand().equals("Ende"))
        {
            ChatServerCaller caller = new ChatServerCaller(server, client);
            caller.start();
            try
            {
                caller.join(100);
            }
            catch(InterruptedException e)
            {
            }
            System.exit(0);
        }
        else
        {
            ChatServerCaller caller = new ChatServerCaller(server, client,
                                                           input.getText());
            caller.start();
            input.setText("");
        }
    }
}

class ChatServerCaller extends Thread
{
    private ChatServer server;
    private ChatClient client;
    private String message;
    private boolean remove; // true: call remove, false: call
                            // sendMessage

    public ChatServerCaller(ChatServer server, ChatClient client,
                            String message, boolean remove)
    {
        this.server = server;
        this.client = client;
        this.message = message;
        this.remove = remove;
    }

    public ChatServerCaller(ChatServer server, ChatClient client)
    {
        this(server, client, null, true);
    }

    public ChatServerCaller(ChatServer server, ChatClient client, String message)
    {
        this(server, client, message, false);
    }

    public void run()
    {
        try
        {
            if(remove)
            {
                server.removeClient(client);
            }
            else
            {
                server.sendMessage(client.getName(), message);
            }
        }
        catch(RemoteException e)
        {
            System.out.println(e);
        }
    }
}