package com.example.myapp.event.handler;

import com.example.myapp.event.RefreshEvent;
import com.google.gwt.event.shared.EventHandler;

public interface RefreshHandler extends EventHandler {

    public void onRefresh(RefreshEvent refreshEvent);
}
