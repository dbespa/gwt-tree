package com.example.myapp.client.uipanel;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;

public interface UiPanelDisplay extends IsWidget {

    void setClickHandler(ClickHandler onClick);

    void setSelectedNodeId(Long id);
}
