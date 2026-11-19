package com.example.myapp.client.tree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.myapp.client.TreeApp;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.event.AddRootClickEvent;
import com.example.myapp.event.ErrorEvent;
import com.example.myapp.event.NodeSelectEvent;
import com.example.myapp.event.RefreshEvent;
import com.example.myapp.event.ToggleChildrenEvent;
import com.example.myapp.event.handler.AddRootClickHandler;
import com.example.myapp.event.handler.NodeSelectHandler;
import com.example.myapp.event.handler.RefreshHandler;
import com.example.myapp.event.handler.ToggleChildrenHandler;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeTreePresenter {

    private static final String ERROR_DATA = "Ошибка данных";

    private NodeRpcServiceAsync nodeRpcService;
    private EventBus eventBus;

    private NodeTreeDisplay view;

    private Set<Long> collapsedNodes;

    public NodeTreePresenter(EventBus eventBus, NodeTreeDisplay view) {
        collapsedNodes = new HashSet<>();

        nodeRpcService = TreeApp.getRpcService();
        this.eventBus = eventBus;

        this.view = view;

        bind();
    }

    private void bind() {
        eventBus.addHandler(RefreshEvent.TYPE, new RefreshHandler() {
            @Override
            public void onRefresh(RefreshEvent refreshEvent) {
                updateTree(refreshEvent.getSelectedNodeId());
            }
        });

        eventBus.addHandler(AddRootClickEvent.TYPE, new AddRootClickHandler() {
            @Override
            public void onAddRootClick(AddRootClickEvent addRootEvent) {
                view.unselectOtherNodes(null);
            }
        });

        view.setNodeSelectHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(NodeSelectEvent nodeSelectEvent) {
                view.unselectOtherNodes(nodeSelectEvent.getSelectedNodeId());
                eventBus.fireEvent(nodeSelectEvent);
            }
        });

        view.setToggleChildrenHandler(new ToggleChildrenHandler() {
            @Override
            public void onToggleChildren(ToggleChildrenEvent toggleChildrenEvent) {
                Long id = toggleChildrenEvent.getSelectedNodeId();
                Boolean isCollapsed = toggleChildrenEvent.isCollapsed();

                if (isCollapsed) {
                    collapsedNodes.add(id);
                } else {
                    collapsedNodes.remove(id);
                }
            }
        });
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public void updateTree(final Long selectedNodeId) {
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
                    eventBus.fireEvent(new ErrorEvent(ERROR_DATA));
                    return;
                }

                NodeTreeViewData convertedTree = convertTree(result);
                view.setTree(convertedTree);

                Set<Long> currentIds = getCurrentIds(result);
                collapsedNodes.retainAll(currentIds);

                view.unselectOtherNodes(selectedNodeId);
                view.collapseNodes(collapsedNodes);
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

        List<NodeTreeBean> children = node.getChildren();
        for (NodeTreeBean nodeBean : children) {
            convertedNode.addChild(convertTree(nodeBean));
        }
        return convertedNode;
    }

    private Set<Long> getCurrentIds(NodeTreeBean node) {
        Set<Long> currentIds = new HashSet<>();

        Long id = node.getId();
        if (id != null) {
            currentIds.add(id);
        }

        List<NodeTreeBean> children = node.getChildren();
        for (NodeTreeBean nodeBean : children) {
            currentIds.addAll(getCurrentIds(nodeBean));
        }

        return currentIds;
    }
}
