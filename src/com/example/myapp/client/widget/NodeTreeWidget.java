package com.example.myapp.client.widget;

import java.util.List;

import com.example.myapp.client.NodeViewData;
import com.example.myapp.client.rpc.NodeRpcServiceAsync;
import com.example.myapp.shared.bean.NodeTreeBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class NodeTreeWidget extends Composite {

    private static final String BUTTON_COLLAPSE = "-";

    private static final String LABEL_NODE = "(.)";
    private static final String LABEL_LEAF = "(*)";

    private static final String STYLE_TOGGLE_BUTTON = "toggle-button";
    private static final String STYLE_TREE_ROW = "tree-row";
    private static final String STYLE_TREE = "tree";
    private static final String STYLE_NODE_CONTAINER = "node-container";
    private static final String STYLE_CHILDREN_CONTAINER = "children-container";
    private static final String STYLE_INDENT = "indent";
    private static final String STYLE_CHILDREN_WRAPPER = "children-wrapper";

    private FlowPanel main;

    private NodeRpcServiceAsync nodeRpcService;

    public NodeTreeWidget(NodeRpcServiceAsync refreshService) {
        nodeRpcService = refreshService;
        build();
        initWidget(main);
    }

    private void build() {
        main = new FlowPanel();
        main.addStyleName(STYLE_TREE);
    }

    public void refreshList() {
        nodeRpcService.getTree(new AsyncCallback<NodeTreeBean>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка получения данных с сервера: " + caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(NodeTreeBean result) {
                NodeViewData convertedTree = convertTree(result);
                setNodeList(convertedTree);
            }
        });
    }

    private NodeViewData convertToNodeViewData(NodeTreeBean node) {
        return new NodeViewData(node.getId(), node.getName());
    }

    private NodeViewData convertTree(NodeTreeBean tree) {
        NodeViewData convertedNode = convertToNodeViewData(tree);

        if (tree.isLeaf()) {
            return convertedNode;
        }

        List<NodeTreeBean> children = tree.getChildren();

        for (NodeTreeBean bean : children) {
            convertedNode.addChild(convertTree(bean));
        }

        return convertedNode;
    }

    private void setNodeList(NodeViewData root) {
        main.clear();

        if (root == null || root.isLeaf()) {
            return;
        }

        List<NodeViewData> rootNodes = root.getChildren();

        for (NodeViewData node : rootNodes) {
            if (node != null) {
                main.add(createNodeContainer(node));
            }
        }
    }

    private FlowPanel createNodeContainer(NodeViewData node) {
        if (node == null) {
            return null;
        }

        FlowPanel nodeContainer = new FlowPanel();
        nodeContainer.addStyleName(STYLE_NODE_CONTAINER);

        FlowPanel nodeItem = createTreeRow(node);
        nodeContainer.add(nodeItem);

        if (node.isLeaf()) {
            return nodeContainer;
        }

        FlowPanel children = createChildrenContainer(node);
        nodeContainer.add(children);

        return nodeContainer;
    }

    private FlowPanel createChildrenContainer(NodeViewData node) {
        if (node == null) {
            return null;
        }

        List<NodeViewData> children = node.getChildren();

        FlowPanel childrenWrapper = new FlowPanel();
        childrenWrapper.addStyleName(STYLE_CHILDREN_WRAPPER);

        FlowPanel indent = new FlowPanel();
        indent.addStyleName(STYLE_INDENT);

        FlowPanel childrenContainer = new FlowPanel();
        childrenContainer.addStyleName(STYLE_CHILDREN_CONTAINER);

        childrenWrapper.add(indent);
        childrenWrapper.add(childrenContainer);

        if (children.isEmpty()) {
            return childrenContainer;
        }

        for (NodeViewData child : children) {
            childrenContainer.add(createNodeContainer(child));
        }

        return childrenWrapper;
    }

    private FlowPanel createTreeRow(NodeViewData node) {
        if (node == null) {
            return null;
        }

        FlowPanel treeRow = new FlowPanel();
        treeRow.addStyleName(STYLE_TREE_ROW);

        Button toggleButton = new Button(BUTTON_COLLAPSE);
        toggleButton.addStyleName(STYLE_TOGGLE_BUTTON);
        treeRow.add(toggleButton);

        treeRow.add(new Label(node.getName()));

        String strNodeType = node.isLeaf() ? LABEL_LEAF : LABEL_NODE;
        Label nodeType = new Label(strNodeType);
        treeRow.add(nodeType);

        return treeRow;
    }
}
