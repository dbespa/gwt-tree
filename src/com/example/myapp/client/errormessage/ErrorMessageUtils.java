package com.example.myapp.client.errormessage;

import com.example.myapp.client.handler.ErrorHandler;
import com.google.gwt.core.client.GWT;

public class ErrorMessageUtils {

    private static ErrorHandler onError;

    public ErrorMessageUtils() {
        //do nothing
    }

    public static void setErrorHandler(ErrorHandler onError) {
        ErrorMessageUtils.onError = onError;
    }

    public static void showErrorMessage(String errorMessage) {
        GWT.log(errorMessage);

        if (onError == null) {
            return;
        }
        onError.onError(errorMessage);
    }
}
