package com.example.myapp.event;

import com.example.myapp.event.handler.AddChildClickHandler;
import com.google.gwt.event.shared.GwtEvent;

public class AddChildClickEvent extends GwtEvent<AddChildClickHandler> {

    public static Type<AddChildClickHandler> TYPE = new Type<AddChildClickHandler>();

    public AddChildClickEvent() {
        // do nothing
    }

    @Override
    public Type<AddChildClickHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddChildClickHandler handler) {
        handler.onAddChildClick(this);
    }
}
