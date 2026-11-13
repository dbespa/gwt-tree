package com.example.myapp.event;

import com.example.myapp.event.handler.ErrorHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ErrorEvent extends GwtEvent<ErrorHandler> {

    public static Type<ErrorHandler> TYPE = new Type<ErrorHandler>();

    private String errorMessage;

    public ErrorEvent() {
        // do nothing
    }

    public ErrorEvent(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public Type<ErrorHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ErrorHandler handler) {
        handler.onError(this);
    }
}
