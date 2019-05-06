package pada.pa.basic;

public class SomethingToRun implements Runnable
{
    public void run()
    {
        System.out.println("Hello World");
    }

    public static void main(String[] args)
    {
        SomethingToRun runner = new SomethingToRun();
        Thread t = new Thread(runner);
        t.start();
    }
}