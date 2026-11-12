package com.example.myapp.client.table;

import java.util.List;

import com.example.myapp.client.TreeApp;
import com.example.myapp.client.errormessage.ErrorMessageUtils;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeTablePresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private NodeTableDisplay view;

    public NodeTablePresenter(NodeTableDisplay view) {
        this.nodeRpcService = TreeApp.getRpcService();
        this.view = view;
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
                ErrorMessageUtils.showErrorMessage(errorMessage);
            }

            @Override
            public void onSuccess(List<NodeBean> result) {
                if (result == null) {
                    view.setNodeList(null);
                    ErrorMessageUtils.showErrorMessage("Ошибка данных");
                    return;
                }
                view.setNodeList(result);
            }
        });
    }
}
