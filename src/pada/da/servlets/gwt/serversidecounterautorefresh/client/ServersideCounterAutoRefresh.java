package pada.da.servlets.gwt.serversidecounterautorefresh.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class ServersideCounterAutoRefresh implements EntryPoint {
    private int counter;
    private Label answerLabel;
    private CounterServiceAsync counterService;

    @Override
    public void onModuleLoad() {
        final Button resetButton = new Button();
        final Button incrementButton = new Button();
        final TextBox inputField = new TextBox();
        this.answerLabel = new Label();
        this.counterService = (CounterServiceAsync) GWT.create(CounterService.class);

        resetButton.setText("Zuruecksetzen");
        resetButton.addClickHandler(new ResetHandler(this.counterService, this));

        incrementButton.setText("Erhoehen");
        incrementButton.addClickHandler(new IncrementHandler(this.counterService, this));

        inputField.addChangeHandler(new InputHandler(inputField, this.counterService, this));

        RootPanel.get("resetButtonContainer").add(resetButton);
        RootPanel.get("incrementButtonContainer").add(incrementButton);
        RootPanel.get("inputFieldContainer").add(inputField);
        RootPanel.get("answerLabelContainer").add(this.answerLabel);

        this.counterService.get(new AsyncHandlerResetIncr(this));
        this.counterService.getIfDifferent(this.counter, new AsyncHandlerGetIfDifferent(this));
    }

    public void onSuccess(final int newValue) {
        this.counter = newValue;
        this.answerLabel.setText("" + newValue);
    }

    public void onSuccessRestart(final int newValue) {
        this.onSuccess(newValue);
        this.counterService.getIfDifferent(this.counter, new AsyncHandlerGetIfDifferent(this));
    }

    public void onFailure(final String message) {
        Window.alert("Fehler: " + message);
    }

    public void onFailureRestart(final String message) {
        this.onFailure(message);
        this.counterService.getIfDifferent(this.counter, new AsyncHandlerGetIfDifferent(this));
    }
}

class ResetHandler implements ClickHandler {
    private final CounterServiceAsync counterService;
    private final ServersideCounterAutoRefresh serversideCounter;

    public ResetHandler(final CounterServiceAsync counterService, final ServersideCounterAutoRefresh serversideCounter) {
        this.counterService = counterService;
        this.serversideCounter = serversideCounter;
    }

    public void onClick(final ClickEvent event) {
        this.counterService.reset(new AsyncHandlerResetIncr(this.serversideCounter));
    }
}

class IncrementHandler implements ClickHandler {
    private final CounterServiceAsync counterService;
    private final ServersideCounterAutoRefresh serversideCounter;

    public IncrementHandler(final CounterServiceAsync counterService,
                            final ServersideCounterAutoRefresh serversideCounter) {
        this.counterService = counterService;
        this.serversideCounter = serversideCounter;
    }

    public void onClick(final ClickEvent event) {
        this.counterService.increment(new AsyncHandlerResetIncr(this.serversideCounter));
    }
}

class InputHandler implements ChangeHandler {
    private final TextBox inputField;
    private final CounterServiceAsync counterService;
    private final ServersideCounterAutoRefresh serversideCounter;

    public InputHandler(final TextBox inputField, final CounterServiceAsync counterService,
                        final ServersideCounterAutoRefresh serversideCounter) {
        this.inputField = inputField;
        this.counterService = counterService;
        this.serversideCounter = serversideCounter;
    }

    public void onChange(final ChangeEvent event) {
        final String inputString = this.inputField.getText();
        this.inputField.setText("");
        try {
            final int newValue = Integer.parseInt(inputString);
            this.counterService.set(newValue, new AsyncHandlerResetIncr(this.serversideCounter));
        } catch (final NumberFormatException e) {
        }
    }
}

class AsyncHandlerResetIncr implements AsyncCallback<Integer> {
    private final ServersideCounterAutoRefresh serversideCounter;

    public AsyncHandlerResetIncr(final ServersideCounterAutoRefresh serversideCounter) {
        this.serversideCounter = serversideCounter;
    }

    @Override
    public void onSuccess(final Integer result) {
        this.serversideCounter.onSuccess(result.intValue());
    }

    @Override
    public void onFailure(final Throwable caught) {
        this.serversideCounter.onFailure(caught.getMessage());
    }
}

class AsyncHandlerGetIfDifferent implements AsyncCallback<Integer> {
    private final ServersideCounterAutoRefresh serversideCounter;

    public AsyncHandlerGetIfDifferent(final ServersideCounterAutoRefresh serversideCounter) {
        this.serversideCounter = serversideCounter;
    }

    @Override
    public void onSuccess(final Integer result) {
        this.serversideCounter.onSuccessRestart(result.intValue());
    }

    @Override
    public void onFailure(final Throwable caught) {
        this.serversideCounter.onFailureRestart(caught.getMessage());
    }
}
