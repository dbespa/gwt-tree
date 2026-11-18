package com.example.myapp.event;

import com.example.myapp.event.handler.ToggleChildrenHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ToggleChildrenEvent extends GwtEvent<ToggleChildrenHandler> {

    public static Type<ToggleChildrenHandler> TYPE = new Type<>();

    private Long id;
    private Boolean isCollapsed;

    public ToggleChildrenEvent() {
        // do nothing
    }

    public ToggleChildrenEvent(Long id, Boolean isCollapsed) {
        this.id = id;
        this.isCollapsed = isCollapsed;
    }

    public Long getSelectedNodeId() {
        return id;
    }

    public Boolean isCollapsed() {
        return isCollapsed;
    }

    @Override
    public Type<ToggleChildrenHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ToggleChildrenHandler handler) {
        handler.onToggleChildren(this);
    }
}
