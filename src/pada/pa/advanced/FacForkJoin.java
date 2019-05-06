package pada.pa.advanced;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class RecursiveFacTask extends RecursiveTask<Integer>
{
    private int n;

    public RecursiveFacTask(int n)
    {
        this.n = n;
    }

    public Integer compute()
    {
        System.out.println("compute(" + n + "): begin [" +
                           Thread.currentThread().getName() + "]");
        if(n == 0)
        {
            return 1;
        }
        RecursiveFacTask rat = new RecursiveFacTask(n-1);
        System.out.println("compute(" + n + "): going to fork [" +
                           Thread.currentThread().getName() + "]");
        rat.fork();
        int r = rat.join();
        System.out.println("compute(" + n + "): joined [" +
                           Thread.currentThread().getName() + "]");
        return n*r;
    }
}

public class FacForkJoin
{
    public static void main(String[] args)
    {
        ForkJoinPool pool = new ForkJoinPool(5);
        RecursiveFacTask task = new RecursiveFacTask(10);
        int result = pool.invoke(task);
        System.out.println("Ergebnis: " + result);
    }
}
