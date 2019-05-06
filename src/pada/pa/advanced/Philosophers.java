package pada.pa.advanced;

class Table
{
    private boolean[] forkUsed;

    private int left(int i)
    {
        return i;
    }

    private int right(int i)
    {
        if(i + 1 < forkUsed.length)
            return i + 1;
        else
            return 0;
    }

    public Table(int number)
    {
        forkUsed = new boolean[number];
        for(int i = 0; i < forkUsed.length; i++)
            forkUsed[i] = false;
    }

    public synchronized void takeFork(int number)
    {
        while(forkUsed[left(number)] || forkUsed[right(number)])
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
            }
        }
        forkUsed[left(number)] = true;
        forkUsed[right(number)] = true;
    }

    public synchronized void putFork(int number)
    {
        forkUsed[left(number)] = false;
        forkUsed[right(number)] = false;
        notifyAll();
    }
}

class Philosopher extends Thread
{
    private Table table;
    private int number;

    public Philosopher(Table table, int number)
    {
        this.table = table;
        this.number = number;
        start();
    }

    public void run()
    {
        while(true)
        {
            think(number);
            table.takeFork(number);
            eat(number);
            table.putFork(number);
        }
    }

    private void think(int number)
    {
        System.out.println("Philosoph " + number + " denkt.");
        try
        {
            sleep((int) (Math.random() * 20000));
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
            sleep((int) (Math.random() * 20000));
        }
        catch(InterruptedException e)
        {
        }
        System.out.println("Philosoph " + number + " hört auf zu essen.");
    }
}

public class Philosophers
{
    private static final int NUMBER = 5;

    public static void main(String[] args)
    {
        Table table = new Table(NUMBER);
        for(int i = 0; i < NUMBER; i++)
            new Philosopher(table, i);
    }
}