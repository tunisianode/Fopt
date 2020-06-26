package pada.da.sockets.seqpar;

import pada.da.sockets.tcp.TCPSocket;

import java.net.ServerSocket;

public class ParallelStaticServer {
    private static final int NUMBER_OF_SLAVES = 20;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Counter counter = new Counter();
        try {
            // create socket
            serverSocket = new ServerSocket(1250);
        } catch (Exception e) {
            System.out.println("Fehler beim Erzeugen des ServerSockets");
            return;
        }
        for (int i = 0; i < NUMBER_OF_SLAVES; i++) {
            Thread t = new StaticSlave(serverSocket, counter);
            t.start();
        }
    }
}

class StaticSlave extends Thread {
    private ServerSocket serverSocket;
    private Counter counter;

    public StaticSlave(ServerSocket serverSocket, Counter counter) {
        this.serverSocket = serverSocket;
        this.counter = counter;
    }

    public void run() {
        while (true) {
            // wait for connection then create streams
            try (TCPSocket tcpSocket = new TCPSocket(serverSocket.accept())) {
                while (true) {
                    String request = tcpSocket.receiveLine();
                    // execute client requests
                    int result;
                    if (request != null) {
                        if (request.equals("increment")) {
                            // perform increment operation
                            result = counter.increment();
                        } else if (request.equals("reset")) {
                            // perform reset operation
                            result = counter.reset();
                            System.out.println("Z�hler wurde zur�ckgesetzt");
                        } else {
                            result = counter.getCounter();
                        }
                        tcpSocket.sendLine("" + result);
                    } else {
                        System.out.println("Schlie�en der Verbindung");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("=> Schlie�en der Verbindung");
            }
        }
    }
}