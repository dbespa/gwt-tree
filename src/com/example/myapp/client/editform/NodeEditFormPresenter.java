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

    private Long currentNodeParentId;
    private Long currentNodeId;

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
            currentNodeParentId = null;
            currentNodeId = null;
            return;
        }

        nodeRpcService.getNodeById(selectedNodeId, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(NodeBean result) {
                if (result == null) {
                    return;
                }
                currentNodeId = result.getId();
                currentNodeParentId = result.getParentId();
                view.setSelectedNode(toNodeDto(result));
            }
        });
    }

    public void createRoot() {
        currentNodeParentId = null;
        currentNodeId = null;

        view.setCreateHeader();
        view.setEmpty();
        view.show();
    }

    public void createChildren() {
        currentNodeParentId = currentNodeId;
        currentNodeId = null;

        view.setCreateHeader();
        view.setEmpty();
        view.show();
    }

    public void editNode() {
        view.setEditHeader();
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
                NodeBean nodeBean = toNodeBean(currentNodeId, currentNodeParentId, node);
                nodeRpcService.save(nodeBean, new AsyncCallback<Void>() {
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

    private NodeBean toNodeBean(Long id, Long parentId, NodeDto nodeDto) {
        String name = nodeDto.getName();
        String ip = nodeDto.getIp();
        Integer port = Integer.parseInt(nodeDto.getPort());
        return new NodeBean(id, parentId, name, ip, port);
    }

    private NodeDto toNodeDto(NodeBean nodeBean) {
        String name = nodeBean.getName();
        String ip = nodeBean.getIp();
        String port = nodeBean.getPort().toString();
        return new NodeDto(name, ip, port);
    }

    public void setRefreshHandler(RefreshHandler onRefresh) {
        this.onRefresh = onRefresh;
    }
}
