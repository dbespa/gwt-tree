package com.example.myapp.event.handler;

import com.example.myapp.event.ToggleChildrenEvent;
import com.google.gwt.event.shared.EventHandler;

public interface ToggleChildrenHandler extends EventHandler {

    public void onToggleChildren(ToggleChildrenEvent toggleChildrenEvent);
}
