package com.example.myapp.client.selectednodeform;

import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class SelectedNodeFormPresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private SelectedNodeFormDisplay view;

    public SelectedNodeFormPresenter(SelectedNodeFormDisplay view) {
        this.nodeRpcService = TreeApp.getRpcService();
        this.view = view;
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public void setSelectedNode(Long selectedNodeId) {
        nodeRpcService.getNodeById(selectedNodeId, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
                view.setEmpty();
            }

            @Override
            public void onSuccess(NodeBean result) {
                if (result != null) {
                    view.setSelectedNode(result);
                }
            }
        });
    }

    public void setEmpty() {
        view.setEmpty();
    }
}
