package pada.da.servlets.gwt.serversidecounterautorefresh.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CounterServiceAsync {
    public void reset(AsyncCallback<Integer> callback);

    public void increment(AsyncCallback<Integer> callback);

    public void set(int newValue, AsyncCallback<Integer> callback);

    public void get(AsyncCallback<Integer> callback);

    void getIfDifferent(final int currentValue, final AsyncCallback<Integer> callback);
}
