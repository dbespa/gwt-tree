package com.example.myapp.event.handler;

import com.example.myapp.event.AfterDeleteEvent;
import com.google.gwt.event.shared.EventHandler;

public interface AfterDeleteHandler extends EventHandler {

    public void onAfterDelete(AfterDeleteEvent afterDeleteEvent);
}
