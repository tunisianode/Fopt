package pada.da.servlets.basic;

public class Counter
{
    private int counter;

    public synchronized int increment()
    {
        counter++;
        return counter;
    }

    public synchronized int reset()
    {
        counter = 0;
        return counter;
    }

    public synchronized void set(int counter)
    {
        this.counter = counter;
    }
}