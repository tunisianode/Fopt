package pp.counter;

public class BoundedCounter
{
    private int zaehler;
    private int min =0;
    private int max =0;

    public BoundedCounter(int min,int max)
    {
        if (min>= max)
        {
            throw new IllegalArgumentException("min muss echt kleiner als max");
        }
        this.zaehler = min ;
        this.min = min;
        this.max = max;
    }

    public synchronized int get()
    {
        return zaehler;
    }

    public synchronized void up()
    {
        while(zaehler == max)
        {
            try
            {
                System.out.println("warten");
                wait();
            }
            catch(InterruptedException ex)
            {
                //
            }
        }
        zaehler ++;
        System.out.println("zahler up "+zaehler );
        notifyAll();
    }

    public synchronized void down()
    {
        while(zaehler == min)
        {
            try
            {
                System.out.println("warten");

                wait();
            }
            catch(InterruptedException ex)
            {
                //
            }
        }
        zaehler --;
        System.out.println("zahler down "+zaehler );
        notifyAll();
    }
}
