package com.example.myapp.event.handler;

import com.example.myapp.event.AddRootClickEvent;
import com.google.gwt.event.shared.EventHandler;

public interface AddRootClickHandler extends EventHandler {

    public void onAddRootClick(AddRootClickEvent addRootEvent);
}
