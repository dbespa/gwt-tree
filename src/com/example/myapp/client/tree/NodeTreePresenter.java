package com.example.myapp.client.tree;

import java.util.List;

import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.event.AddRootClickEvent;
import com.example.myapp.event.AfterDeleteEvent;
import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.NodeSelectEvent;
import com.example.myapp.event.handler.AddRootClickHandler;
import com.example.myapp.event.handler.AfterDeleteHandler;
import com.example.myapp.event.handler.NodeSelectHandler;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeTreePresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private EventBus eventBus;

    private NodeTreeDisplay view;

    private Long selectedNodeId;

    public NodeTreePresenter(EventBus eventBus, NodeTreeDisplay view) {
        nodeRpcService = TreeApp.getRpcService();
        this.eventBus = eventBus;

        this.view = view;

        bind();
    }

    private void bind() {
        eventBus.addHandler(NodeSelectEvent.TYPE, new NodeSelectHandler() {
            @Override
            public void onNodeSelect(NodeSelectEvent nodeSelectEvent) {
                if (nodeSelectEvent.getSelectedNodeId() == null) {
                    setSelectedNode(null);
                }
            }
        });

        eventBus.addHandler(AddRootClickEvent.TYPE, new AddRootClickHandler() {
            @Override
            public void onAddRootClick(AddRootClickEvent addRootEvent) {
                setSelectedNode(null);
            }
        });

        eventBus.addHandler(AfterDeleteEvent.TYPE, new AfterDeleteHandler() {
            public void onAfterDelete(AfterDeleteEvent afterDeleteEvent) {
                setSelectedNode(null);
                updateTree();
            }
        });

        view.setNodeSelectHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(NodeSelectEvent nodeSelectEvent) {
                Long selectedNodeId = nodeSelectEvent.getSelectedNodeId();
                NodeTreePresenter.this.selectedNodeId = selectedNodeId;
                view.unselectOtherNodes(selectedNodeId);

                eventBus.fireEvent(nodeSelectEvent);
            }
        });
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public Long getSelectedNodeId() {
        return selectedNodeId;
    }

    public void setSelectedNode(Long selectedNodeId) {
        this.selectedNodeId = selectedNodeId;

        if (selectedNodeId == null) {
            setAllNodesUnselected();
        }
    }

    public void updateTree() {
        nodeRpcService.getTree(new AsyncCallback<NodeTreeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка получения данных с сервера: " + caught.getLocalizedMessage();
                eventBus.fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(NodeTreeBean result) {
                if (result == null) {
                    view.setTree(null);
                    eventBus.fireEvent(new ErrorEvent("Ошибка данных"));
                    return;
                }
                NodeTreeViewData convertedTree = convertTree(result);
                view.setTree(convertedTree);
                view.unselectOtherNodes(selectedNodeId);
            }
        });
    }

    private NodeTreeViewData convertToNodeViewData(NodeTreeBean node) {
        if (node == null) {
            return null;
        }
        return new NodeTreeViewData(node.getId(), node.getName());
    }

    private NodeTreeViewData convertTree(NodeTreeBean node) {
        if (node == null) {
            return null;
        }

        NodeTreeViewData convertedNode = convertToNodeViewData(node);

        if (node.isEmpty()) {
            return convertedNode;
        }

        List<NodeTreeBean> children = node.getChildren();
        for (NodeTreeBean nodeBean : children) {
            convertedNode.addChild(convertTree(nodeBean));
        }
        return convertedNode;
    }

    public void setAllNodesUnselected() {
        selectedNodeId = null;
        view.unselectOtherNodes(null);
    }
}
