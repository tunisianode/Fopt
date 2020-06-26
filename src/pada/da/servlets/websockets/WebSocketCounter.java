package pada.da.servlets.websockets;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Counter {
    private int counter;

    public synchronized int increment() {
        counter++;
        return counter;
    }

    public synchronized int reset() {
        counter = 0;
        return counter;
    }

    public synchronized int get() {
        return counter;
    }
}

@ServerEndpoint("/websocket/counter")
public class WebSocketCounter {
    private static Counter c = new Counter();
    private static List<WebSocketCounter> connections =
            Collections.synchronizedList(new LinkedList<WebSocketCounter>());

    private Session session;

    @OnOpen
    public void start(Session s) {
        this.session = s;
        connections.add(this);
        broadcast("someone joined");
    }


    @OnClose
    public void end() {
        connections.remove(this);
        broadcast("someone left");
    }


    @OnMessage
    public void incoming(String message) {
        // Never trust the client
        String filteredMessage = message.toString();
        int value;
        if (filteredMessage.equals("increment")) {
            value = c.increment();
        } else if (filteredMessage.equals("reset")) {
            value = c.reset();
        } else {
            value = c.get();
        }
        broadcast("counter = " + value);
    }


    private static void broadcast(String msg) {
        for (WebSocketCounter client : connections) {
            try {
                client.session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                }
                broadcast("someone has been disconnected");
            }
        }
    }
}
