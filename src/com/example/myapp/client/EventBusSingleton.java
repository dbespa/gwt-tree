package com.example.myapp.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

public class EventBusSingleton {

    private static EventBus eventBus;

    private EventBusSingleton() {
        // do nothing
    }

    public static EventBus getEventBus() {
        if (eventBus == null) {
            eventBus = new SimpleEventBus();
        }
        return eventBus;
    }
}
