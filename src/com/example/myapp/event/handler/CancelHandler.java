package com.example.myapp.event.handler;

import com.example.myapp.event.CancelEvent;
import com.google.gwt.event.shared.EventHandler;

public interface CancelHandler extends EventHandler {

    public void onCancel(CancelEvent cancelEvent);
}
