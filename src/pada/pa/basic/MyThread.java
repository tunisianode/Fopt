package pada.pa.basic;

public class MyThread extends Thread
{
    public void run()
    {
        System.out.println("Hello World");
    }

    public static void main(String[] args)
    {
        MyThread t = new MyThread();
        t.start();
    }
}