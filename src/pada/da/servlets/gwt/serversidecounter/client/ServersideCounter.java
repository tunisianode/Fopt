package pada.da.servlets.gwt.serversidecounter.client;

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

public class ServersideCounter implements EntryPoint {
    private Label answerLabel;

    public void onModuleLoad() {
        final Button resetButton = new Button();
        final Button incrementButton = new Button();
        final TextBox inputField = new TextBox();
        this.answerLabel = new Label();
        final CounterServiceAsync counterService = (CounterServiceAsync) GWT.create(CounterService.class);

        resetButton.setText("Zuruecksetzen");
        resetButton.addClickHandler(new ResetHandler(counterService, this));

        incrementButton.setText("Erhoehen");
        incrementButton.addClickHandler(new IncrementHandler(counterService, this));

        inputField.addChangeHandler(new InputHandler(inputField, counterService, this));

        RootPanel.get("resetButtonContainer").add(resetButton);
        RootPanel.get("incrementButtonContainer").add(incrementButton);
        RootPanel.get("inputFieldContainer").add(inputField);
        RootPanel.get("answerLabelContainer").add(this.answerLabel);

        counterService.get(new AsyncHandler(this));
    }

    public void onSuccess(final int newValue) {
        this.answerLabel.setText("" + newValue);
    }

    public void onFailure(final String message) {
        Window.alert("Fehler: " + message);
    }
}

class ResetHandler implements ClickHandler {
    private final CounterServiceAsync counterService;
    private final ServersideCounter serversideCounter;

    public ResetHandler(final CounterServiceAsync counterService, final ServersideCounter serversideCounter) {
        this.counterService = counterService;
        this.serversideCounter = serversideCounter;
    }

    public void onClick(final ClickEvent event) {
        this.counterService.reset(new AsyncHandler(this.serversideCounter));
    }
}

class IncrementHandler implements ClickHandler {
    private final CounterServiceAsync counterService;
    private final ServersideCounter serversideCounter;

    public IncrementHandler(final CounterServiceAsync counterService, final ServersideCounter serversideCounter) {
        this.counterService = counterService;
        this.serversideCounter = serversideCounter;
    }

    public void onClick(final ClickEvent event) {
        this.counterService.increment(new AsyncHandler(this.serversideCounter));
    }
}

class InputHandler implements ChangeHandler {
    private final TextBox inputField;
    private final CounterServiceAsync counterService;
    private final ServersideCounter serversideCounter;

    public InputHandler(final TextBox inputField, final CounterServiceAsync counterService,
                        final ServersideCounter serversideCounter) {
        this.inputField = inputField;
        this.counterService = counterService;
        this.serversideCounter = serversideCounter;
    }

    public void onChange(final ChangeEvent event) {
        final String inputString = this.inputField.getText();
        this.inputField.setText("");
        try {
            final int newValue = Integer.parseInt(inputString);
            this.counterService.set(newValue, new AsyncHandler(this.serversideCounter));
        } catch (final NumberFormatException e) {
        }
    }
}

class AsyncHandler implements AsyncCallback<Integer> {
    private final ServersideCounter serversideCounter;

    public AsyncHandler(final ServersideCounter serversideCounter) {
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
