package pada.pa.advanced;

public class PhilosopherWithSemaphoreGroup extends Thread {
    private SemaphoreGroup sems;
    private int leftFork;
    private int rightFork;

    public PhilosopherWithSemaphoreGroup(SemaphoreGroup sems, int number) {
        this.sems = sems;
        leftFork = number;
        if (number + 1 < sems.getNumberOfMembers())
            rightFork = number + 1;
        else
            rightFork = 0;
        start();
    }

    public void run() {
        int[] deltas = new int[sems.getNumberOfMembers()];
        for (int i = 0; i < deltas.length; i++)
            deltas[i] = 0;
        int number = leftFork;
        while (true) {
            think(number);
            deltas[leftFork] = -1;
            deltas[rightFork] = -1;
            sems.changeValues(deltas);
            eat(number);
            deltas[leftFork] = 1;
            deltas[rightFork] = 1;
            sems.changeValues(deltas);
        }
    }

    private void think(int number) {
        System.out.println("Philosoph " + number + " denkt.");
        try {
            sleep((int) (Math.random() * 20000));
        } catch (InterruptedException e) {
        }
    }

    private void eat(int number) {
        System.out.println("Philosoph " + number + " f�ngt zu essen an.");
        try {
            sleep((int) (Math.random() * 20000));
        } catch (InterruptedException e) {
        }
        System.out.println("Philosoph " + number + " h�rt auf zu essen.");
    }

    private static final int NUMBER = 5;

    public static void main(String[] args) {
        SemaphoreGroup forks = new SemaphoreGroup(NUMBER);
        int[] init = new int[NUMBER];
        for (int i = 0; i < NUMBER; i++) {
            init[i] = 1;
        }
        forks.changeValues(init);
        for (int i = 0; i < NUMBER; i++) {
            new PhilosopherWithSemaphoreGroup(forks, i);
        }
    }
}