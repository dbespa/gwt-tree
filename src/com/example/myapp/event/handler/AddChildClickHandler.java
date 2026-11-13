package com.example.myapp.event.handler;

import com.example.myapp.event.AddChildClickEvent;
import com.google.gwt.event.shared.EventHandler;

public interface AddChildClickHandler extends EventHandler {

    public void onAddChildClick(AddChildClickEvent addChildClickEvent);
}
