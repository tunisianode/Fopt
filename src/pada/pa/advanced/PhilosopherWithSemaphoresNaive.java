package pada.pa.advanced;

public class PhilosopherWithSemaphoresNaive implements Runnable
{
    private Semaphore[] sems;
    private int number;
    private int left, right;

    public PhilosopherWithSemaphoresNaive(Semaphore[] sems, int number)
    {
        this.sems = sems;
        this.number = number;
        left = number;
        right = number + 1;
        if(right == sems.length)
            right = 0;
    }

    public void run()
    {
        while(true)
        {
            think(number);
            sems[left].p();
            sems[right].p();
            eat(number);
            sems[left].v();
            sems[right].v();
        }
    }

    private void think(int number)
    {
        System.out.println("Philosoph " + number + " denkt.");
        try
        {
            Thread.sleep((int) (Math.random() * 20000));
        }
        catch(InterruptedException e)
        {
        }
    }

    private void eat(int number)
    {
        System.out.println("Philosoph " + number + " fängt zu essen an.");
        try
        {
            Thread.sleep((int) (Math.random() * 20000));
        }
        catch(InterruptedException e)
        {
        }
        System.out.println("Philosoph " + number + " hört auf zu essen.");
    }
    private static final int NUMBER = 5;

    public static void main(String[] args)
    {
        Semaphore[] sems = new Semaphore[NUMBER];
        for(int i = 0; i < NUMBER; i++)
            sems[i] = new Semaphore(1);
        for(int i = 0; i < NUMBER; i++)
            new Thread(new PhilosopherWithSemaphoresNaive(sems, i)).start();
    }
}