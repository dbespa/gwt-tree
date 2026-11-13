package com.example.myapp.event.handler;

import com.example.myapp.event.NodeSelectEvent;
import com.google.gwt.event.shared.EventHandler;

public interface NodeSelectHandler extends EventHandler {

    public void onNodeSelect(NodeSelectEvent nodeSelectEvent);
}
