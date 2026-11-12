package com.example.myapp.client.editform;

import com.example.myapp.client.StringUtils;
import com.example.myapp.client.TreeApp;
import com.example.myapp.client.errormessage.ErrorMessageUtils;
import com.example.myapp.client.handler.RefreshHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeBean;
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

    private void setCancelHandler() {
        view.setCancelHandler(new CancelHandler() {
            @Override
            public void onCancel() {
                view.setSelectedNode(null);
                view.hide();
            }
        });
    }

    private void setSaveHandler() {
        view.setSaveHandler(new SaveHandler() {
            @Override
            public void onSave(NodeEditFormViewData node) {
                if (node == null) {
                    view.setSelectedNode(null);
                    String errorMessage = "Ошибка данных";
                    ErrorMessageUtils.showErrorMessage(errorMessage);
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

    // TODO mativ посмотреть как избавится от бойлера. Есть идея полу-фасада
    public void setCreateRootMode() {
        currentNodeParentId = null;
        setCreateMode();
    }

    public void setCreateChildMode(Long selectedNodeId) {
        currentNodeParentId = selectedNodeId;
        setCreateMode();
    }

    private void setCreateMode() {
        currentNodeId = null;
        view.showCreateForm();
    }

    public void setEditNodeMode(final Long selectedNodeId) {
        nodeRpcService.getNodeById(selectedNodeId, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка получения данных с сервера: " + caught.getLocalizedMessage();
                ErrorMessageUtils.showErrorMessage(errorMessage);
            }

            @Override
            public void onSuccess(NodeBean result) {
                if (result == null) {
                    String errorMessage = "Ошибка данных";
                    ErrorMessageUtils.showErrorMessage(errorMessage);
                    return;
                }

                currentNodeParentId = result.getParentId();
                currentNodeId = selectedNodeId;
                view.showEditForm(toNodeViewData(result));
            }
        });
    }

    public void setRefreshHandler(RefreshHandler onRefresh) {
        this.onRefresh = onRefresh;
    }

    private void saveNodeBean(NodeBean nodeBean) {
        if (nodeBean == null) {
            view.setSelectedNode(null);
            ErrorMessageUtils.showErrorMessage("Ошибка данных");
            return;
        }

        nodeRpcService.save(nodeBean, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка отправки данных на сервер: " + caught.getLocalizedMessage();
                ErrorMessageUtils.showErrorMessage(errorMessage);
            }

            @Override
            public void onSuccess(Void result) {
                view.setSelectedNode(null);
                view.hide();

                if (onRefresh == null) {
                    return;
                }
                onRefresh.onRefresh();
            }
        });
    }

    private NodeBean toNodeBean(Long id, Long parentId, NodeEditFormViewData node) {
        if (node == null) {
            return null;
        }

        String name = node.getName();
        String ip = node.getIp();

        Integer port = StringUtils.parseIntQuiet(node.getPort());
        return new NodeBean(id, parentId, name, ip, port);
    }

    private NodeEditFormViewData toNodeViewData(NodeBean node) {
        if (node == null) {
            return null;
        }

        String name = node.getName();
        String ip = node.getIp();

        Integer portValue = node.getPort();
        String port = portValue == null ? null : portValue.toString();
        return new NodeEditFormViewData(name, ip, port);
    }

    private void focusInvalidField(NodeEditFormViewData node) {
        if (node == null) {
            return;
        }

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
