package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.bean.NodeViewData;
import com.example.myapp.client.handler.NodeSelectHandler;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class NodeTreeWidget extends Composite {

    private static final String STYLE_TREE = "tree";

    private FlowPanel main;

    private NodeRpcServiceAsync nodeRpcService;

    private NodeSelectHandler onNodeSelect;

    private NodeTreeRowWidget virtualRoot;

    public NodeTreeWidget(NodeRpcServiceAsync refreshService) {
        nodeRpcService = refreshService;
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_TREE);
    }

    public void setNodeSelectHandler(NodeSelectHandler handler) {
        this.onNodeSelect = handler;
    }

    public void refreshTree() {
        nodeRpcService.getTree(new AsyncCallback<NodeTreeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(NodeTreeBean result) {
                NodeViewData convertedTree = convertTree(result);
                setTree(convertedTree);
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

    private void setTree(NodeViewData tree) {
        main.clear();

        if (tree == null || tree.isEmpty()) {
            return;
        }

        virtualRoot = new NodeTreeRowWidget(tree);

        NodeSelectHandler onSelect = new NodeSelectHandler() {
            @Override
            public void onNodeSelect(Long selectedNodeId) {
                NodeSelectHandler handler = NodeTreeWidget.this.onNodeSelect;
                unselectOtherNodes(selectedNodeId);
                if (handler != null) {
                    handler.onNodeSelect(selectedNodeId);
                }
            }
        };

        virtualRoot.setNodeSelectHandler(onSelect);
        virtualRoot.setCaptionVisible(false);
        main.add(virtualRoot);
    }

    private void unselectOtherNodes(Long selectedNodeId) {
        virtualRoot.unselectOtherNodes(selectedNodeId);
    }
}
