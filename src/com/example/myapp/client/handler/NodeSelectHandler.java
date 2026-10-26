package com.example.myapp.client.handler;

import com.google.gwt.event.shared.EventHandler;

public interface NodeSelectHandler extends EventHandler {

    public void onNodeSelect(Long selectedNodeId);
}
