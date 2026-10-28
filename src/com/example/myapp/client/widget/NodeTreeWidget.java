package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.UniversalRoot;
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
    private UniversalRoot universalRoot;

    private NodeRpcServiceAsync nodeRpcService;

    private NodeSelectHandler onNodeSelect;

    public NodeTreeWidget(NodeRpcServiceAsync refreshService) {
        universalRoot = null;
        nodeRpcService = refreshService;
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_TREE);
        universalRoot = new UniversalRoot(main);
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

        List<NodeViewData> rootNodes = tree.getChildren();

        for (NodeViewData node : rootNodes) {
            if (node != null) {
                NodeTreeRowWidget nodeRow = new NodeTreeRowWidget(node);
                universalRoot.add(nodeRow);

                universalRoot.setNodeSelectHandler(new NodeSelectHandler() {
                    @Override
                    public void onNodeSelect(Long selectedNodeId) {
                        setOtherNodesUnselected(selectedNodeId);
                        NodeTreeWidget.this.onNodeSelect.onNodeSelect(selectedNodeId);
                    }
                });
                universalRoot.add(nodeRow);
            }
        }
    }

    private void setOtherNodesUnselected(Long selectedNodeId) {
        universalRoot.setOtherNodesUnselected(selectedNodeId);
    }
}
