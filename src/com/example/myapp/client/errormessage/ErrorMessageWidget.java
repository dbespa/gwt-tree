package com.example.myapp.client.errormessage;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class ErrorMessageWidget {

    private Label errorMessageLabel;
    private PopupPanel errorPopup;

    public ErrorMessageWidget() {
        errorPopup = new PopupPanel(true);
        errorMessageLabel = new Label();

        errorPopup.setWidget(errorMessageLabel);
    }

    public void showErrorMessage(String errorMessage) {
        errorMessageLabel.setText(errorMessage);
        errorPopup.show();
    }
}
