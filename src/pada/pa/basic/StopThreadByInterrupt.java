package pada.pa.basic;

public class StopThreadByInterrupt extends Thread {
    public StopThreadByInterrupt() {
        start();
    }

    public void run() {
        int i = 0;
        try {
            while (!isInterrupted()) {
                i++;
                System.out.println("Hallo Welt (" + i + ")");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Thread endet jetzt ...");
    }

    public static void main(String[] args) {
        StopThreadByInterrupt st = new StopThreadByInterrupt();
        try {
            Thread.sleep(9100);
        } catch (InterruptedException e) {
        }
        st.interrupt();
    }
}