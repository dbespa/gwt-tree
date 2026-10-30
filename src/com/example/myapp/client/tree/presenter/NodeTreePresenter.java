package com.example.myapp.client.tree.presenter;

import java.util.List;

import com.example.myapp.client.bean.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class NodeTreePresenter {

    private NodeRpcServiceAsync nodeRpcService;
    private Display display;

    public interface Display {
        void setTree(NodeViewData tree);

        void setNodeSelectHandler(NodeSelectHandler onSelect);

        void unselectOtherNodes(Long selectedNodeId);

        Widget asWidget();
    }

    public NodeTreePresenter(NodeRpcServiceAsync nodeRpcService, Display view) {
        this.nodeRpcService = nodeRpcService;
        this.display = view;
    }

    public void go(HasWidgets widget) {
        widget.add(display.asWidget());
    }

    public void getTree() {
        nodeRpcService.getTree(new AsyncCallback<NodeTreeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(NodeTreeBean result) {
                NodeViewData convertedTree = convertTree(result);
                display.setTree(convertedTree);
            }
        });
    }

    public void setNodeSelectHandler(final NodeSelectHandler onSelect) {
        display.setNodeSelectHandler(new NodeSelectHandler() {
            @Override
            public void onNodeSelect(Long selectedNodeId) {
                display.unselectOtherNodes(selectedNodeId);
                if (onSelect != null) {
                    onSelect.onNodeSelect(selectedNodeId);
                }
            }
        });
    }

    private NodeViewData convertToNodeViewData(NodeTreeBean node) {
        return new NodeViewData(node.getId(), node.getName());
    }

    private NodeViewData convertTree(NodeTreeBean node) {
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
