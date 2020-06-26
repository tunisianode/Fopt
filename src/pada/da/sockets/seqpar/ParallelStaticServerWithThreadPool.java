package pada.da.sockets.seqpar;

import pada.da.sockets.tcp.TCPSocket;

import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {
    private TCPSocket socket;
    private Counter counter;

    public Task(TCPSocket socket, Counter counter) {
        this.socket = socket;
        this.counter = counter;
    }

    public void run() {
        try (TCPSocket s = socket) {
            while (true) {
                String request = socket.receiveLine();
                // execute client requests
                int result;
                if (request != null) {
                    if (request.equals("increment")) {
                        // perform increment operation
                        result = counter.increment();
                    } else if (request.equals("reset")) {
                        // perform reset operation
                        result = counter.reset();
                    } else {
                        result = counter.getCounter();
                    }
                    socket.sendLine("" + result);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

public class ParallelStaticServerWithThreadPool {
    public static void main(String[] args) {
        Counter counter = new Counter();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3,
                3,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        // create socket
        try (ServerSocket serverSocket = new ServerSocket(1250)) {
            while (true) {
                try {
                    // wait for connection then create streams
                    TCPSocket tcpSocket = new TCPSocket(serverSocket.accept());
                    Task task = new Task(tcpSocket, counter);
                    pool.execute(task);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Erzeugen oder Nutzen des ServerSockets");
        }
    }
}