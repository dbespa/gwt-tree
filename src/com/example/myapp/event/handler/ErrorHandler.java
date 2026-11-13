package com.example.myapp.event.handler;

import com.example.myapp.event.ErrorEvent;
import com.google.gwt.event.shared.EventHandler;

public interface ErrorHandler extends EventHandler {

    public void onError(ErrorEvent errorEvent);
}
