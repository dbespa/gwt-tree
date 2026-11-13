package com.example.myapp.event;

import com.example.myapp.event.handler.AfterDeleteHandler;
import com.google.gwt.event.shared.GwtEvent;

public class AfterDeleteEvent extends GwtEvent<AfterDeleteHandler> {

    public static Type<AfterDeleteHandler> TYPE = new Type<AfterDeleteHandler>();

    public AfterDeleteEvent() {
        // do nothing
    }

    @Override
    public Type<AfterDeleteHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AfterDeleteHandler handler) {
        handler.onAfterDelete(this);
    }
}
