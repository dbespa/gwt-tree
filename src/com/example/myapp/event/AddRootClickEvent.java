package com.example.myapp.event;

import com.example.myapp.event.handler.AddRootClickHandler;
import com.google.gwt.event.shared.GwtEvent;

public class AddRootClickEvent extends GwtEvent<AddRootClickHandler> {

    public static Type<AddRootClickHandler> TYPE = new Type<AddRootClickHandler>();

    public AddRootClickEvent() {
        // do nothing
    }

    @Override
    public Type<AddRootClickHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddRootClickHandler handler) {
        handler.onAddRootClick(this);
    }
}
