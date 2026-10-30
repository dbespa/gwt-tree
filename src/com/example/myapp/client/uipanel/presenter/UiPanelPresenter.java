package com.example.myapp.client.uipanel.presenter;

import com.example.myapp.client.handler.RefreshHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class UiPanelPresenter {

    private Display display;

    public interface Display {
        void setClickHandler(ClickHandler onClick);

        void setSelectedNodeId(Long id);

        Widget asWidget();
    }

    public UiPanelPresenter(Display view) {
        this.display = view;
    }

    public void go(HasWidgets widget) {
        widget.add(display.asWidget());
    }

    public void setSelectedNodeId(Long id) {
        display.setSelectedNodeId(id);
    }

    public void setRefreshHandler(final RefreshHandler onRefresh) {
        display.setClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onRefresh.onRefresh();
            }
        });
    }
}
