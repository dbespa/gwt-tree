package com.example.myapp.client.editform;

import com.example.myapp.client.handler.CancelHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NodeEditFormPresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private NodeEditFormDisplay view;

    public NodeEditFormPresenter(NodeRpcServiceAsync nodeRpcService, NodeEditFormDisplay view) {
        this.nodeRpcService = nodeRpcService;
        this.view = view;

        if (view == null) {
            return;
        }

        setCancelHandler();
    }

    public void setSelectedNode(Long selectedNodeId) {
        nodeRpcService.getNodeById(selectedNodeId, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(NodeBean result) {
                view.setSelectedNode(result);
            }
        });
    }

    public void showCreateForm() {
        view.setCreateHeader();
        view.setEmpty();
        view.show();
    }

    public void setCancelHandler() {
        view.setCancelHandler(new CancelHandler() {
            @Override
            public void onCancel() {
                view.setEmpty();
                view.hide();
            }
        });
    }
}
