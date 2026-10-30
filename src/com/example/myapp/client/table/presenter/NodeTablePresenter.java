package com.example.myapp.client.table.presenter;

import java.util.List;

import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class NodeTablePresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private Display display;

    public interface Display {
        void setNodeList(List<NodeBean> newNodeList);

        Widget asWidget();
    }

    public NodeTablePresenter(NodeRpcServiceAsync nodeRpcService, Display view) {
        this.nodeRpcService = nodeRpcService;
        this.display = view;
    }

    public void go(HasWidgets widget) {
        widget.add(display.asWidget());
    }

    public void getTable() {
        nodeRpcService.getAllNodes(new AsyncCallback<List<NodeBean>>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<NodeBean> result) {
                display.setNodeList(result);
            }
        });
    }
}
