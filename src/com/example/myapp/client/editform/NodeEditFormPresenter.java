package com.example.myapp.client.editform;

import com.example.myapp.client.EventBusSingleton;
import com.example.myapp.client.StringUtils;
import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.event.AddChildClickEvent;
import com.example.myapp.event.AddRootClickEvent;
import com.example.myapp.event.CancelEvent;
import com.example.myapp.event.EditClickEvent;
import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.RefreshEvent;
import com.example.myapp.event.SaveEvent;
import com.example.myapp.event.handler.AddChildClickHandler;
import com.example.myapp.event.handler.AddRootClickHandler;
import com.example.myapp.event.handler.CancelHandler;
import com.example.myapp.event.handler.EditClickHandler;
import com.example.myapp.event.handler.SaveHandler;
import com.example.myapp.shared.bean.NodeBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NodeEditFormPresenter {

    private static final String ERROR_DATA = "Ошибка данных";

    private NodeRpcServiceAsync nodeRpcService;

    private NodeEditFormDisplay view;
    private NodeEditFormValidator validator;

    private Long currentNodeParentId;
    private Long currentNodeId;

    public NodeEditFormPresenter(NodeEditFormValidator validator, NodeEditFormDisplay view) {
        nodeRpcService = TreeApp.getRpcService();
        this.validator = validator;

        this.view = view;

        bind();
    }

    private void bind() {
        EventBusSingleton.getInstance().addHandler(AddRootClickEvent.TYPE, new AddRootClickHandler() {
            @Override
            public void onAddRootClick(AddRootClickEvent addRootEvent) {
                setCreateRootMode();
            }
        });

        EventBusSingleton.getInstance().addHandler(AddChildClickEvent.TYPE, new AddChildClickHandler() {
            @Override
            public void onAddChildClick(AddChildClickEvent addChildClickEvent) {
                setCreateChildMode(addChildClickEvent.getSelectedNodeId());
            }
        });

        EventBusSingleton.getInstance().addHandler(EditClickEvent.TYPE, new EditClickHandler() {
            @Override
            public void onEditClick(EditClickEvent editClickEvent) {
                setEditNodeMode(editClickEvent.getSelectedNodeId());
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
                    EventBusSingleton.getInstance().fireEvent(new ErrorEvent(ERROR_DATA));
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

    private void setCreateRootMode() {
        setCreateMode(null);
    }

    private void setCreateChildMode(Long selectedNodeId) {
        setCreateMode(selectedNodeId);
    }

    private void setCreateMode(Long parentNodeId) {
        currentNodeParentId = parentNodeId;
        currentNodeId = null;
        view.showCreateForm();
    }

    private void setEditNodeMode(final Long selectedNodeId) {
        nodeRpcService.getNodeById(selectedNodeId, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка получения данных с сервера: " + caught.getLocalizedMessage();
                EventBusSingleton.getInstance().fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(NodeBean result) {
                if (result == null) {
                    EventBusSingleton.getInstance().fireEvent(new ErrorEvent(ERROR_DATA));
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
            EventBusSingleton.getInstance().fireEvent(new ErrorEvent(ERROR_DATA));
            return;
        }

        nodeRpcService.save(nodeBean, new AsyncCallback<NodeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка отправки данных на сервер: " + caught.getLocalizedMessage();
                EventBusSingleton.getInstance().fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(NodeBean result) {
                view.setSelectedNode(null);
                view.hide();

                EventBusSingleton.getInstance().fireEvent(new RefreshEvent(result.getId()));
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
