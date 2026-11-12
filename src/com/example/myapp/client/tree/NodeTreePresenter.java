package com.example.myapp.client.tree;

import java.util.List;

import com.example.myapp.client.TreeApp;
import com.example.myapp.client.errormessage.ErrorMessageUtils;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeTreePresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private NodeTreeDisplay view;

    private Long selectedNodeId;

    public NodeTreePresenter(NodeTreeDisplay view) {
        this.nodeRpcService = TreeApp.getRpcService();
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
                NodeTreePresenter.this.selectedNodeId = selectedNodeId;
                view.unselectOtherNodes(selectedNodeId);

                if (onSelect == null) {
                    return;
                }
                onSelect.onNodeSelect(selectedNodeId);
            }
        });
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
                ErrorMessageUtils.showErrorMessage(errorMessage);
            }

            @Override
            public void onSuccess(NodeTreeBean result) {
                if (result == null) {
                    view.setTree(null);
                    ErrorMessageUtils.showErrorMessage("Ошибка данных");
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
