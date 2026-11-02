package com.example.myapp.client.uipanel;

import com.example.myapp.client.handler.RefreshHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasWidgets;

public class UiPanelPresenter {

    private UiPanelDisplay view;

    public UiPanelPresenter(UiPanelDisplay view) {
        this.view = view;
    }

    public void go(HasWidgets container) {
        container.add(view.asWidget());
    }

    public void setSelectedNodeId(Long id) {
        view.setSelectedNodeId(id);
    }

    public void setRefreshHandler(final RefreshHandler onRefresh) {
        view.setClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onRefresh.onRefresh();
            }
        });
    }
}
