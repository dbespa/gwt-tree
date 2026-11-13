package com.example.myapp.event.handler;

import com.example.myapp.event.SaveEvent;
import com.google.gwt.event.shared.EventHandler;

public interface SaveHandler extends EventHandler {

    public void onSave(SaveEvent saveEvent);
}
