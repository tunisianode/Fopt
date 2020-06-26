package pada.da.servlets.gwt.serversidecounterautorefresh.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import pada.da.servlets.gwt.serversidecounterautorefresh.client.CounterService;

public class CounterServiceImpl extends RemoteServiceServlet implements CounterService {
    private static final long MAX_TIME = 30000;

    private int counter;

    @Override
    public synchronized int reset() {
        this.counter = 0;
        this.notifyAll();
        return this.counter;
    }

    @Override
    public synchronized int increment() {
        this.counter++;
        this.notifyAll();
        return this.counter;
    }

    @Override
    public synchronized int set(final int newValue) {
        this.counter = newValue;
        this.notifyAll();
        return this.counter;
    }

    @Override
    public synchronized int get() {
        return this.counter;
    }

    @Override
    public synchronized int getIfDifferent(final int currentValue) {
        final long startTime = System.currentTimeMillis();
        while ((this.counter == currentValue) && (System.currentTimeMillis() - startTime < CounterServiceImpl.MAX_TIME)) {
            try {
                this.wait(CounterServiceImpl.MAX_TIME);
            } catch (final Exception e) {
            }
        }
        return this.counter;
    }
}
