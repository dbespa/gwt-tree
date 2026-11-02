package com.example.myapp.client.tree;

import java.util.List;

import com.example.myapp.client.bean.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeTreePresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private NodeTreeDisplay view;

    public NodeTreePresenter(NodeRpcServiceAsync nodeRpcService, NodeTreeDisplay view) {
        this.nodeRpcService = nodeRpcService;
        this.view = view;
    }

    public void go(HasWidgets container) {
        if (container == null) {
            return;
        }
        container.add(view.asWidget());
    }

    public void setNodeSelectHandler(final NodeSelectHandler onSelect) {
        view.setNodeSelectHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(Long selectedNodeId) {
                view.unselectOtherNodes(selectedNodeId);
                if (onSelect != null) {
                    onSelect.onNodeSelect(selectedNodeId);
                }
            }
        });
    }

    public void updateTree() {
        nodeRpcService.getTree(new AsyncCallback<NodeTreeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(NodeTreeBean result) {
                NodeViewData convertedTree = convertTree(result);
                view.setTree(convertedTree);
            }
        });
    }

    private NodeViewData convertToNodeViewData(NodeTreeBean node) {
        if (node == null) {
            return null;
        }
        return new NodeViewData(node.getId(), node.getName());
    }

    private NodeViewData convertTree(NodeTreeBean node) {
        if (node == null) {
            return null;
        }

        NodeViewData convertedNode = convertToNodeViewData(node);

        if (node.isEmpty()) {
            return convertedNode;
        }

        List<NodeTreeBean> children = node.getChildren();
        for (NodeTreeBean nodeBean : children) {
            convertedNode.addChild(convertTree(nodeBean));
        }
        return convertedNode;
    }
}
