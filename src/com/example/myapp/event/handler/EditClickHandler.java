package com.example.myapp.event.handler;

import com.example.myapp.event.EditClickEvent;
import com.google.gwt.event.shared.EventHandler;

public interface EditClickHandler extends EventHandler {

    public void onEditClick(EditClickEvent editClickEvent);
}
