package com.example.myapp.client.uipanel;

import com.example.myapp.client.TreeApp;
import com.example.myapp.client.handler.AddChildClickHandler;
import com.example.myapp.client.handler.AddRootClickHandler;
import com.example.myapp.client.handler.AfterDeleteHandler;
import com.example.myapp.client.handler.EditClickHandler;
import com.example.myapp.client.handler.RefreshHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class UiPanelPresenter {

    private UiPanelDisplay view;
    private NodeRpcServiceAsync nodeRpcService;

    private Long selectedId;

    public UiPanelPresenter(UiPanelDisplay view) {
        this.nodeRpcService = TreeApp.getRpcService();
        this.view = view;
        view.setButtonsActive(false);
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public void setSelectedNodeId(Long id) {
        selectedId = id;
        view.setSelectedNodeId(id);
        view.setButtonsActive(id != null);
    }

    public void setRefreshHandler(final RefreshHandler onRefresh) {
        view.setRefreshHandler(onRefresh);
    }

    public void setAddRootClickHandler(final AddRootClickHandler onAddRootClick) {
        view.setAddRootClickHandler(onAddRootClick);
    }

    public void setAddChildClickHandler(final AddChildClickHandler onAddChildClick) {
        view.setAddChildClickHandler(onAddChildClick);
    }

    public void setEditClickHandler(final EditClickHandler onEditClick) {
        view.setEditClickHandler(onEditClick);
    }

    public void setAfterDeleteHandler(final AfterDeleteHandler onAfterDelete) {
        view.setAfterDeleteHandler(new AfterDeleteHandler() {
            @Override
            public void onAfterDelete() {
                nodeRpcService.deleteNode(selectedId, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("Ошибка сервера: " + caught.getLocalizedMessage());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        view.setSelectedNodeId(null);
                        onAfterDelete.onAfterDelete();
                    }
                });
            }
        });
    }
}
