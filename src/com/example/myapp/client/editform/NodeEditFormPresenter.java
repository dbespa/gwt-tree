package com.example.myapp.client.editform;

import com.example.myapp.client.TreeApp;
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
    private NodeEditFormValidator validator;

    private RefreshHandler onRefresh;

    private Long currentNodeParentId;
    private Long currentNodeId;

    public NodeEditFormPresenter(NodeEditFormValidator validator, NodeEditFormDisplay view) {
        this.nodeRpcService = TreeApp.getRpcService();
        this.view = view;
        this.validator = validator;

        bind();
    }

    private void bind() {
        setCancelHandler();
        setSaveHandler();
    }

    // TODO mativ посмотреть как избавится от бойлера. Есть идея полу-фасада
    public void createRoot() {
        currentNodeParentId = null;
        currentNodeId = null;

        view.setCreateHeader();
        view.setSelectedNode(null);
        view.show();
    }

    public void createChild(Long selectedNodeId) {
        currentNodeParentId = selectedNodeId;
        currentNodeId = null;

        view.setCreateHeader();
        view.setSelectedNode(null);
        view.show();
    }

    public void editNode(final Long selectedNodeId) {
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

                currentNodeParentId = result.getParentId();
                currentNodeId = selectedNodeId;
                view.setSelectedNode(toNodeViewData(result));
                view.setEditHeader();
                view.show();
            }
        });
    }

    public void setRefreshHandler(RefreshHandler onRefresh) {
        this.onRefresh = onRefresh;
    }

    private void setCancelHandler() {
        view.setCancelHandler(new CancelHandler() {
            @Override
            public void onCancel() {
                view.setAllFieldsValid();
                view.setSelectedNode(null);
                view.hide();
            }
        });
    }

    private void setSaveHandler() {
        view.setSaveHandler(new SaveHandler() {
            @Override
            public void onSave(NodeViewData node) {
                if (node == null) {
                    return;
                }

                if (!validator.isValid(node)) {
                    focusInvalidField(node);
                    return;
                }

                NodeBean nodeBean = toNodeBean(currentNodeId, currentNodeParentId, node);
                saveNodeBean(nodeBean);
            }
        });
    }

    private void saveNodeBean(NodeBean nodeBean) {
        nodeRpcService.save(nodeBean, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка отправки данных на сервер: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(Void result) {
                view.hide();
                view.setAllFieldsValid();
                if (onRefresh == null) {
                    return;
                }
                onRefresh.onRefresh();
            }
        });
    }

    private NodeBean toNodeBean(Long id, Long parentId, NodeViewData node) {
        if (node == null) {
            return null;
        }

        String name = node.getName();
        String ip = node.getIp();
        Integer port;
        try {
            port = Integer.parseInt(node.getPort());
        } catch (Exception e) {
            GWT.log("Ошибка конвертации: " + e.getLocalizedMessage());
            port = null;
        }
        return new NodeBean(id, parentId, name, ip, port);
    }

    private NodeViewData toNodeViewData(NodeBean node) {
        if (node == null) {
            return null;
        }

        String name = node.getName();
        String ip = node.getIp();

        Integer portValue = node.getPort();
        String port = portValue == null ? null : portValue.toString();
        return new NodeViewData(name, ip, port);
    }

    private void focusInvalidField(NodeViewData node) {
        view.setAllFieldsValid();

        if (!validator.nameIsValid(node.getName())) {
            view.setNameFieldInvalid();
            return;
        }

        if (!validator.ipIsValid(node.getIp())) {
            view.setIpFieldInvalid();
            return;
        }

        if (!validator.portIsValid(node.getPort())) {
            view.setPortFieldInvalid();
            return;
        }
    }
}
