package com.example.myapp.client.uipanel;

import com.example.myapp.client.handler.AddChildHandler;
import com.example.myapp.client.handler.AddRootHandler;
import com.example.myapp.client.handler.RefreshHandler;
import com.google.gwt.user.client.ui.HasWidgets;

public class UiPanelPresenter {

    private UiPanelDisplay view;

    public UiPanelPresenter(UiPanelDisplay view) {
        this.view = view;
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public void setSelectedNodeId(Long id) {
        view.setSelectedNodeId(id);
    }

    public void setRefreshHandler(final RefreshHandler onRefresh) {
        view.setRefreshHandler(onRefresh);
    }

    public void setAddRootNodeHandler(final AddRootHandler onAddRoot) {
        view.setAddRootHandler(onAddRoot);
    }

    public void setAddChildNodeHandler(final AddChildHandler onAddChild) {
        view.setAddChildHandler(onAddChild);
    }
}
