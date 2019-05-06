package pada.pa.advanced;

import java.util.concurrent.Executor;

public class ThreadPerTaskExecutor implements Executor
{
    public void execute(Runnable r)
    {
        Thread t = new Thread(r);
        t.start();
    }
}