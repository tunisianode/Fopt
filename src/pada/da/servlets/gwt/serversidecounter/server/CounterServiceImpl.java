package pada.da.servlets.gwt.serversidecounter.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import pada.da.servlets.gwt.serversidecounter.client.CounterService;

public class CounterServiceImpl extends RemoteServiceServlet implements CounterService
{
    private int counter;

    @Override
    public synchronized int reset()
    {
        this.counter = 0;
        return this.counter;
    }

    @Override
    public synchronized int increment()
    {
        this.counter++;
        return this.counter;
    }

    @Override
    public synchronized int set(final int newValue)
    {
        this.counter = newValue;
        return this.counter;
    }

    @Override
    public synchronized int get()
    {
        return this.counter;
    }
}
