package com.example.myapp.client.editform;

import com.example.myapp.client.StringUtils;
import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.event.AddRootClickEvent;
import com.example.myapp.event.CancelEvent;
import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.RefreshEvent;
import com.example.myapp.event.SaveEvent;
import com.example.myapp.event.handler.AddRootClickHandler;
import com.example.myapp.event.handler.CancelHandler;
import com.example.myapp.event.handler.SaveHandler;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NodeEditFormPresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private EventBus eventBus;

    private NodeEditFormDisplay view;
    private NodeEditFormValidator validator;

    private Long currentNodeParentId;
    private Long currentNodeId;

    public NodeEditFormPresenter(EventBus eventBus, NodeEditFormValidator validator, NodeEditFormDisplay view) {
        nodeRpcService = TreeApp.getRpcService();

        this.eventBus = eventBus;
        this.validator = validator;
        this.view = view;

        bind();
    }

    private void bind() {
        eventBus.addHandler(AddRootClickEvent.TYPE, new AddRootClickHandler() {
            @Override
            public void onAddRootClick(AddRootClickEvent addRootEvent) {
                setCreateRootMode();
            }
        });

        view.setCancelHandler(new CancelHandler() {
            @Override
            public void onCancel(CancelEvent cancelEvent) {
                view.setSelectedNode(null);
                view.hide();
            }
        });

        view.setSaveHandler(new SaveHandler() {
            @Override
            public void onSave(SaveEvent saveEvent) {
                NodeEditFormViewData node = saveEvent.getNode();

                if (node == null) {
                    view.setSelectedNode(null);
                    String errorMessage = "Ошибка данных";
                    eventBus.fireEvent(new ErrorEvent(errorMessage));
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

    public void setCreateRootMode() {
        setCreateMode(null);
    }

    public void setCreateChildMode(Long selectedNodeId) {
        setCreateMode(selectedNodeId);
    }

    private void setCreateMode(Long parentNodeId) {
        currentNodeParentId = parentNodeId;
        currentNodeId = null;
        view.showCreateForm();
    }

    public void setEditNodeMode(final Long selectedNodeId) {
        nodeRpcService.getNodeById(selectedNodeId, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка получения данных с сервера: " + caught.getLocalizedMessage();
                eventBus.fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(NodeBean result) {
                if (result == null) {
                    String errorMessage = "Ошибка данных";
                    eventBus.fireEvent(new ErrorEvent(errorMessage));
                    return;
                }

                currentNodeParentId = result.getParentId();
                currentNodeId = selectedNodeId;
                view.showEditForm(toNodeViewData(result));
            }
        });
    }

    private void saveNodeBean(NodeBean nodeBean) {
        if (nodeBean == null) {
            view.setSelectedNode(null);
            eventBus.fireEvent(new ErrorEvent("Ошибка данных"));
            return;
        }

        nodeRpcService.save(nodeBean, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка отправки данных на сервер: " + caught.getLocalizedMessage();
                eventBus.fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(Void result) {
                view.setSelectedNode(null);
                view.hide();

                eventBus.fireEvent(new RefreshEvent());
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
