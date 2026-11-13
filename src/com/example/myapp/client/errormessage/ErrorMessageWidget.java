package com.example.myapp.client.errormessage;

import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.handler.ErrorHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class ErrorMessageWidget {

    private Label errorMessageLabel;
    private PopupPanel errorPopup;

    private EventBus eventBus;

    public ErrorMessageWidget(EventBus eventBus) {
        this.eventBus = eventBus;

        errorPopup = new PopupPanel(true);
        errorMessageLabel = new Label();

        errorPopup.setWidget(errorMessageLabel);

        this.eventBus.addHandler(ErrorEvent.TYPE, new ErrorHandler() {
            @Override
            public void onError(ErrorEvent errorEvent) {
                showErrorMessage(errorEvent.getErrorMessage());
            }
        });
    }

    private void showErrorMessage(String errorMessage) {
        GWT.log(errorMessage);
        errorMessageLabel.setText(errorMessage);
        errorPopup.show();
    }
}
