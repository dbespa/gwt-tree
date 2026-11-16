package com.example.myapp.event;

import com.example.myapp.event.handler.EditClickHandler;
import com.google.gwt.event.shared.GwtEvent;

public class EditClickEvent extends GwtEvent<EditClickHandler> {

    public static Type<EditClickHandler> TYPE = new Type<EditClickHandler>();

    private Long selectedNodeId;

    public EditClickEvent() {
        // do nothing
    }

    public EditClickEvent(Long selectedNodeId) {
        this.selectedNodeId = selectedNodeId;
    }

    public Long getSelectedNodeId() {
        return selectedNodeId;
    }

    @Override
    public Type<EditClickHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditClickHandler handler) {
        handler.onEditClick(this);
    }
}
