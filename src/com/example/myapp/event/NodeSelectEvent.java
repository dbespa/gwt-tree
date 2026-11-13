package com.example.myapp.event;

import com.example.myapp.event.handler.NodeSelectHandler;
import com.google.gwt.event.shared.GwtEvent;

public class NodeSelectEvent extends GwtEvent<NodeSelectHandler> {

    public static Type<NodeSelectHandler> TYPE = new Type<NodeSelectHandler>();
    private Long selectedNodeId;

    public NodeSelectEvent() {
        // do nothing
    }

    public NodeSelectEvent(Long selectedNodeId) {
        this.selectedNodeId = selectedNodeId;
    }

    public Long getSelectedNodeId() {
        return selectedNodeId;
    }

    @Override
    public Type<NodeSelectHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(NodeSelectHandler handler) {
        handler.onNodeSelect(this);
    }
}
