package com.example.myapp.event;

import com.example.myapp.event.handler.RefreshHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RefreshEvent extends GwtEvent<RefreshHandler> {

    public static Type<RefreshHandler> TYPE = new Type<RefreshHandler>();

    public RefreshEvent() {
        // do nothing
    }

    @Override
    public Type<RefreshHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RefreshHandler handler) {
        handler.onRefresh(this);
    }
}
