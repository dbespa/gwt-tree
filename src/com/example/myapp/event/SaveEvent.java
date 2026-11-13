package com.example.myapp.event;

import com.example.myapp.client.editform.NodeEditFormViewData;
import com.example.myapp.event.handler.SaveHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SaveEvent extends GwtEvent<SaveHandler> {

    public static Type<SaveHandler> TYPE = new Type<SaveHandler>();

    private NodeEditFormViewData node;

    public SaveEvent() {
        // do nothing
    }

    public SaveEvent(NodeEditFormViewData node) {
        this.node = node;
    }

    public NodeEditFormViewData getNode() {
        return node;
    }

    @Override
    public Type<SaveHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SaveHandler handler) {
        handler.onSave(this);
    }
}
