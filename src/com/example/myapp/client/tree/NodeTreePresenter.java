package com.example.myapp.client.tree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.myapp.client.EventBusSingleton;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeTreePresenter {

    private static final String ERROR_DATA = "Ошибка данных";

    private NodeRpcServiceAsync nodeRpcService;

    private NodeTreeDisplay view;

    private Set<Long> collapsedNodes;

    public NodeTreePresenter(NodeTreeDisplay view) {
        collapsedNodes = new HashSet<>();

        nodeRpcService = TreeApp.getRpcService();

        this.view = view;

        bind();
    }

    private void bind() {
        EventBusSingleton.getInstance().addHandler(RefreshEvent.TYPE, new RefreshHandler() {
            @Override
            public void onRefresh(RefreshEvent refreshEvent) {
                updateTree(refreshEvent.getSelectedNodeId());
            }
        });

        EventBusSingleton.getInstance().addHandler(AddRootClickEvent.TYPE, new AddRootClickHandler() {
            @Override
            public void onAddRootClick(AddRootClickEvent addRootEvent) {
                view.unselectOtherNodes(null);
            }
        });

        view.setNodeSelectHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(NodeSelectEvent nodeSelectEvent) {
                view.unselectOtherNodes(nodeSelectEvent.getSelectedNodeId());
                EventBusSingleton.getInstance().fireEvent(nodeSelectEvent);
            }
        });

        view.setToggleChildrenHandler(new ToggleChildrenHandler() {
            @Override
            public void onToggleChildren(ToggleChildrenEvent toggleChildrenEvent) {
                Long id = toggleChildrenEvent.getToggledNodeId();
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
        clear();

        nodeRpcService.getTree(new AsyncCallback<NodeTreeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                String errorMessage = "Ошибка получения данных с сервера: " + caught.getLocalizedMessage();
                EventBusSingleton.getInstance().fireEvent(new ErrorEvent(errorMessage));
            }

            @Override
            public void onSuccess(NodeTreeBean result) {
                if (result == null) {
                    EventBusSingleton.getInstance().fireEvent(new ErrorEvent(ERROR_DATA));
                    return;
                }

                NodeTreeViewData convertedTree = convertTree(result);
                view.setTree(convertedTree);

                view.unselectOtherNodes(selectedNodeId);

                if (collapsedNodes.isEmpty()) {
                    return;
                }

                Set<Long> currentIds = getCurrentIds(result);
                collapsedNodes.retainAll(currentIds);
                view.collapseNodes(collapsedNodes);
            }
        });
    }

    private void clear() {
        view.setTree(null);
        view.unselectOtherNodes(null);
        collapsedNodes.clear();
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
