package pada.da.sockets.seqpar;

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

    public synchronized int getCounter()
    {
        return counter;
    }
}
