package com.example.myapp.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;

public class EventBusSingleton {

    private static EventBusSingleton instance;
    private EventBus eventBus;

    private EventBusSingleton() {
        eventBus = new SimpleEventBus();
    }

    public static EventBusSingleton getInstance() {
        if (instance == null) {
            instance = new EventBusSingleton();
        }
        return instance;
    }

    public <H extends EventHandler> void fireEvent(GwtEvent<H> event) {
        eventBus.fireEvent(event);
    }

    public <H extends EventHandler> void addHandler(GwtEvent.Type<H> type, H handler) {
        eventBus.addHandler(type, handler);
    }
}
