package com.example.myapp.client.table;

import java.util.List;

import com.example.myapp.client.EventBusSingleton;
import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.RefreshEvent;
import com.example.myapp.event.handler.RefreshHandler;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeTablePresenter {

    private static final String ERROR_DATA = "Ошибка данных";

    private NodeRpcServiceAsync nodeRpcService;

    private NodeTableDisplay view;

    public NodeTablePresenter(NodeTableDisplay view) {
        nodeRpcService = TreeApp.getRpcService();

        this.view = view;

        bind();
    }

    private void bind() {
        EventBusSingleton.getInstance().addHandler(RefreshEvent.TYPE, new RefreshHandler() {
            @Override
            public void onRefresh(RefreshEvent refreshEvent) {
                updateTable();
            }
        });
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public void updateTable() {
        nodeRpcService.getAllNodes(new AsyncCallback<List<NodeBean>>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка получения данных с сервера: " + caught.getLocalizedMessage();
                EventBusSingleton.getInstance().fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(List<NodeBean> result) {
                if (result == null) {
                    view.setNodeList(null);
                    EventBusSingleton.getInstance().fireEvent(new ErrorEvent(ERROR_DATA));
                    return;
                }
                view.setNodeList(result);
            }
        });
    }
}
