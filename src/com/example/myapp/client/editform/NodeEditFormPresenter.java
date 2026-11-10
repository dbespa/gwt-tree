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
    private RefreshHandler onRefresh;

    private Long currentNodeParentId;
    private Long currentNodeId;

    public NodeEditFormPresenter(NodeEditFormDisplay view) {
        this.nodeRpcService = TreeApp.getRpcService();
        this.view = view;
        setCancelHandler();
        setSaveHandler();
    }

    public void setSelectedNode(Long selectedNodeId) {
        if (selectedNodeId == null) {
            currentNodeParentId = null;
            currentNodeId = null;
            return;
        }

        currentNodeId = selectedNodeId;
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
        nodeRpcService.getNodeById(currentNodeId, new AsyncCallback<NodeBean>() {
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
                view.setSelectedNode(toNodeViewData(result));
                view.setEditHeader();
                view.show();
            }
        });
    }

    public void setRefreshHandler(RefreshHandler onRefresh) {
        this.onRefresh = onRefresh;
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
            public void onSave(NodeViewData node) {
                if (node == null) {
                    return;
                }

                if (!isValid(node)) {
                    focusInvalidField(node);
                    return;
                }

                NodeBean nodeBean = toNodeBean(currentNodeId, currentNodeParentId, node);
                nodeRpcService.save(nodeBean, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("Ошибка отправки данных на сервер: " + caught.getLocalizedMessage());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        view.hide();
                        if (onRefresh == null) {
                            return;
                        }
                        onRefresh.onRefresh();
                    }
                });
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

    private Boolean isValid(NodeViewData node) {
        return nameIsValid(node.getName())
                && ipIsValid(node.getIp())
                && portIsValid(node.getPort());
    }

    private Boolean nameIsValid(String name) {
        return name != null && !name.trim().isEmpty();
    }

    private Boolean ipIsValid(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }

        String[] parts = ip.trim().split("\\.");

        if (parts.length != 4) {
            return false;
        }

        for (int i = 0; i < parts.length; i++) {
            try {
                Integer part = Integer.parseInt(parts[i]);
                if (part < 0 || part > 255) {
                    return false;
                }

            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private Boolean portIsValid(String port) {
        if (port == null || port.trim().isEmpty()) {
            return false;
        }

        Integer portInt = null;
        try {
            portInt = Integer.parseInt(port.trim());

            if (portInt < 0 || portInt > 65535) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    void focusInvalidField(NodeViewData node) {
        view.setAllFieldsValid();

        if (!nameIsValid(node.getName())) {
            view.setNameFieldInvalid();
            return;
        }

        if (!ipIsValid(node.getIp())) {
            view.setIpFieldInvalid();
            return;
        }

        if (!portIsValid(node.getPort())) {
            view.setPortFieldInvalid();
            return;
        }
    }
}
