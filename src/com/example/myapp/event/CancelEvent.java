package com.example.myapp.event;

import com.example.myapp.event.handler.CancelHandler;
import com.google.gwt.event.shared.GwtEvent;

public class CancelEvent extends GwtEvent<CancelHandler> {

    public static Type<CancelHandler> TYPE = new Type<CancelHandler>();

    public CancelEvent() {
        // do nothing
    }

    @Override
    public Type<CancelHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CancelHandler handler) {
        handler.onCancel(this);
    }
}
