package com.example.myapp.client.table;

import java.util.List;

import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeTablePresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private NodeTableDisplay view;

    public NodeTablePresenter(NodeRpcServiceAsync nodeRpcService, NodeTableDisplay view) {
        this.nodeRpcService = nodeRpcService;
        this.view = view;
    }

    public void go(HasWidgets container) {
        container.add(view.asWidget());
    }

    public void updateTable() {
        nodeRpcService.getAllNodes(new AsyncCallback<List<NodeBean>>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<NodeBean> result) {
                view.setNodeList(result);
            }
        });
    }
}
