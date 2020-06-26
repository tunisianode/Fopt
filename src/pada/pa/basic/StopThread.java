package pada.pa.basic;

public class StopThread extends Thread {
    private boolean stopped = false;

    public StopThread() {
        start();
    }

    public synchronized void stopThread() {
        stopped = true;
    }

    public synchronized boolean isStopped() {
        return stopped;
    }

    public void run() {
        int i = 0;
        while (!isStopped()) {
            i++;
            System.out.println("Hallo Welt (" + i + ")");
        }
        System.out.println("Thread endet jetzt ...");
    }

    public static void main(String[] args) {
        StopThread st = new StopThread();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        st.stopThread();
    }
}