package pada.da.servlets.gwt.serversidecounterautorefresh.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("counter")
public interface CounterService extends RemoteService {
    public int reset();

    public int increment();

    public int set(int newValue);

    public int get();

    int getIfDifferent(final int currentValue);
}
