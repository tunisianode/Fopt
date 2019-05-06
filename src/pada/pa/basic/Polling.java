package pada.pa.basic;

public class Polling extends Thread
{
    public Polling()
    {
        start();
    }

    public void run()
    {
        int i = 0;
        while(!isInterrupted())
        {
            i++;
            System.out.println("Hallo Welt (" + i + ")");
        }
    }

    public static void main(String[] args)
    {
        Polling p = new Polling();
        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException e)
        {
        }
        System.out.println("isAlive liefert: " + p.isAlive());
        int i = 0;
        p.interrupt();
        while(p.isAlive())
        {
            i++;
            System.out.println("Thread lebt immer noch");
        }
        System.out.println("Thread lebte noch " + i + 
                           " Durchläufe/Durchlauf");
    }
}