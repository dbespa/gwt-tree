package com.example.myapp.client.editform;

import com.example.myapp.client.handler.CancelHandler;
import com.example.myapp.client.handler.RefreshHandler;
import com.example.myapp.client.handler.SaveHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NodeEditFormPresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private NodeEditFormDisplay view;
    private RefreshHandler onRefresh;

    private Long parentId;

    public NodeEditFormPresenter(NodeRpcServiceAsync nodeRpcService, NodeEditFormDisplay view) {
        this.nodeRpcService = nodeRpcService;
        this.view = view;

        if (view == null) {
            return;
        }

        setCancelHandler();
        setSaveHandler();
    }

    public void setSelectedNode(Long selectedNodeId) {
        if (selectedNodeId == null) {
            this.parentId = null;
            return;
        }

        nodeRpcService.getNodeById(selectedNodeId, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(NodeBean result) {
                parentId = result.getId();
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

    public void setSaveHandler() {
        view.setSaveHandler(new SaveHandler() {
            @Override
            public void onSave(NodeDto node) {
                NodeBean nodeBean = toNodeBean(node);
                nodeRpcService.send(nodeBean, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("Ошибка отправки данных на сервер: " + caught.getLocalizedMessage());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        onRefresh.onRefresh();
                    }
                });
                view.hide();
            }
        });
    }

    private NodeBean toNodeBean(NodeDto nodeDto) {
        Long parentId = this.parentId;

        String name = nodeDto.getName();
        String ip = nodeDto.getIp();
        Integer port = Integer.parseInt(nodeDto.getPort());

        return new NodeBean(null, parentId, name, ip, port);
    }

    public void setRefreshHandler(RefreshHandler onRefresh) {
        this.onRefresh = onRefresh;
    }
}
