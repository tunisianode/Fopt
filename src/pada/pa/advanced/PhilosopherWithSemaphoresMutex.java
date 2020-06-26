package pada.pa.advanced;

public class PhilosopherWithSemaphoresMutex implements Runnable {
    private Semaphore mutex;
    private int number;

    public PhilosopherWithSemaphoresMutex(Semaphore mutex, int platz) {
        this.mutex = mutex;
        this.number = platz;
    }

    public void run() {
        while (true) {
            think(number);
            mutex.p();
            eat(number);
            mutex.v();
        }
    }

    private void think(int number) {
        System.out.println("Philosoph " + number + " denkt.");
        try {
            Thread.sleep((int) (Math.random() * 20000));
        } catch (InterruptedException e) {
        }
    }

    private void eat(int number) {
        System.out.println("Philosoph " + number + " f�ngt zu essen an.");
        try {
            Thread.sleep((int) (Math.random() * 20000));
        } catch (InterruptedException e) {
        }
        System.out.println("Philosoph " + number + " h�rt auf zu essen.");
    }

    private static final int NUMBER = 5;

    public static void main(String[] args) {
        Semaphore mutex = new Semaphore(1);
        for (int i = 0; i < NUMBER; i++)
            new Thread(new PhilosopherWithSemaphoresMutex(mutex, i)).start();
    }
}