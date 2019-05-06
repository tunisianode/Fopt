package pada.pa.advanced;

import java.util.concurrent.Executor;

public class SelfExecutor implements Executor
{
    public void execute(Runnable r)
    {
        r.run();
    }
}